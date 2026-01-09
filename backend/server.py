from fastapi import FastAPI, APIRouter, HTTPException, Depends, status, UploadFile, File, Form
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from dotenv import load_dotenv
from starlette.middleware.cors import CORSMiddleware
from motor.motor_asyncio import AsyncIOMotorClient
from contextlib import asynccontextmanager
import os
import logging
from pathlib import Path
from pydantic import BaseModel, Field, ConfigDict, EmailStr
from typing import List, Optional
import uuid
from datetime import datetime, timezone, timedelta
from passlib.context import CryptContext
from jose import JWTError, jwt
import shutil

# Import the AI service
from ai_service import ai_service

ROOT_DIR = Path(__file__).parent
load_dotenv(ROOT_DIR / '.env')

# MongoDB connection
mongo_url = os.environ['MONGO_URL']
client = AsyncIOMotorClient(mongo_url)
db = client[os.environ['DB_NAME']]

# --- LIFESPAN MANAGER (Replaces on_event) ---
@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup logic: Database is already initialized globally above
    yield
    # Shutdown logic: Close database connection
    client.close()
    logging.info("MongoDB connection closed.")

# Create the main app with lifespan
app = FastAPI(lifespan=lifespan)
api_router = APIRouter(prefix="/api")

# Security
pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")
security = HTTPBearer()

# JWT settings
SECRET_KEY = os.environ.get('SECRET_KEY', 'your-secret-key-change-in-production')
ALGORITHM = "HS256"
ACCESS_TOKEN_EXPIRE_MINUTES = 60 * 24 * 7  # 7 days

# Ensure uploads directory exists
UPLOADS_DIR = ROOT_DIR / 'uploads'
UPLOADS_DIR.mkdir(exist_ok=True)

# --- MODELS ---
class User(BaseModel):
    model_config = ConfigDict(extra="ignore")
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    name: str
    email: EmailStr
    password_hash: str
    wallet_balance: float = 10000.0  # Default starting balance
    created_at: datetime = Field(default_factory=lambda: datetime.now(timezone.utc))

class UserCreate(BaseModel):
    name: str
    email: EmailStr
    password: str

class UserLogin(BaseModel):
    email: EmailStr
    password: str

class UserResponse(BaseModel):
    id: str
    name: str
    email: str
    wallet_balance: float = 0.0

class Token(BaseModel):
    token: str
    user: UserResponse

class Transaction(BaseModel):
    model_config = ConfigDict(extra="ignore")
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    user_id: str
    amount: float
    date: str
    category: str
    method: str  # payment method: Cash, UPI, Debit Card, Credit Card
    note: Optional[str] = ""
    receipt_url: Optional[str] = None
    type: str = "expense"  # expense or income
    created_at: datetime = Field(default_factory=lambda: datetime.now(timezone.utc))

class TransactionCreate(BaseModel):
    amount: float
    date: str
    category: str
    method: str
    note: Optional[str] = ""
    type: str = "expense"

class TransactionUpdate(BaseModel):
    amount: Optional[float] = None
    date: Optional[str] = None
    category: Optional[str] = None
    method: Optional[str] = None
    note: Optional[str] = None

class Category(BaseModel):
    model_config = ConfigDict(extra="ignore")
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    user_id: str
    name: str
    icon: Optional[str] = "📁"
    color: Optional[str] = "#667eea"
    created_at: datetime = Field(default_factory=lambda: datetime.now(timezone.utc))

class CategoryCreate(BaseModel):
    name: str
    icon: Optional[str] = "📁"
    color: Optional[str] = "#667eea"

class Budget(BaseModel):
    model_config = ConfigDict(extra="ignore")
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    user_id: str
    category: str
    amount: float
    period: str  # monthly, weekly, yearly
    start_date: str
    end_date: str
    created_at: datetime = Field(default_factory=lambda: datetime.now(timezone.utc))

class BudgetCreate(BaseModel):
    category: str
    amount: float
    period: str
    start_date: str
    end_date: str

class OverviewStats(BaseModel):
    wallet_balance: float
    monthly_income: float
    monthly_spending: float
    active_budgets: int
    upcoming_bills: int

class WalletAdjust(BaseModel):
    amount: float
    type: str  # add or deduct

# AI Request Models
class AICategorizeRequest(BaseModel):
    text: str
    amount: Optional[float] = None
    date: Optional[str] = None

class AISuggestExpenseRequest(BaseModel):
    text: str
    amount: Optional[float] = None

class AISuggestBudgetRequest(BaseModel):
    months: int = 3
    categories: Optional[List[str]] = None

class AISearchRequest(BaseModel):
    query: str

class AIInsightsRequest(BaseModel):
    range: dict  # {"from": "YYYY-MM-DD", "to": "YYYY-MM-DD"}
    options: Optional[dict] = None

class AIForecastRequest(BaseModel):
    months: int = 1
    categories: Optional[List[str]] = None


# --- HELPER FUNCTIONS ---
def hash_password(password: str) -> str:
    return pwd_context.hash(password)

def verify_password(plain_password: str, hashed_password: str) -> bool:
    return pwd_context.verify(plain_password, hashed_password)

def create_access_token(data: dict) -> str:
    to_encode = data.copy()
    expire = datetime.now(timezone.utc) + timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES)
    to_encode.update({"exp": expire})
    encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
    return encoded_jwt

async def get_current_user(credentials: HTTPAuthorizationCredentials = Depends(security)) -> dict:
    try:
        token = credentials.credentials
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        user_id: str = payload.get("sub")
        if user_id is None:
            raise HTTPException(status_code=401, detail="Invalid authentication credentials")
        return {"id": user_id}
    except JWTError:
        raise HTTPException(status_code=401, detail="Invalid authentication credentials")


# --- AUTH ROUTES ---
@api_router.post("/auth/register", response_model=Token)
async def register(user_data: UserCreate):
    # Check if user exists
    existing_user = await db.users.find_one({"email": user_data.email})
    if existing_user:
        raise HTTPException(status_code=400, detail="Email already registered")
    
    # Create user
    user = User(
        name=user_data.name,
        email=user_data.email,
        password_hash=hash_password(user_data.password),
        wallet_balance=10000.0  # Starting balance
    )
    
    user_dict = user.model_dump()
    user_dict['created_at'] = user_dict['created_at'].isoformat()
    await db.users.insert_one(user_dict)
    
    # Create default categories
    default_categories = [
        {"name": "Food & Dining", "icon": "🍽️", "color": "#ef4444"},
        {"name": "Transportation", "icon": "🚗", "color": "#3b82f6"},
        {"name": "Shopping", "icon": "🛒", "color": "#8b5cf6"},
        {"name": "Entertainment", "icon": "🎬", "color": "#ec4899"},
        {"name": "Bills & Utilities", "icon": "💡", "color": "#f59e0b"},
        {"name": "Health", "icon": "🏥", "color": "#10b981"},
        {"name": "Income", "icon": "💰", "color": "#22c55e"},
    ]
    
    for cat_data in default_categories:
        category = Category(user_id=user.id, **cat_data)
        cat_dict = category.model_dump()
        cat_dict['created_at'] = cat_dict['created_at'].isoformat()
        await db.categories.insert_one(cat_dict)
    
    # Create token
    access_token = create_access_token(data={"sub": user.id})
    
    return Token(
        token=access_token,
        user=UserResponse(id=user.id, name=user.name, email=user.email, wallet_balance=user.wallet_balance)
    )

@api_router.post("/auth/login", response_model=Token)
async def login(user_data: UserLogin):
    user = await db.users.find_one({"email": user_data.email})
    if not user or not verify_password(user_data.password, user["password_hash"]):
        raise HTTPException(status_code=401, detail="Incorrect email or password")
    
    access_token = create_access_token(data={"sub": user["id"]})
    
    return Token(
        token=access_token,
        user=UserResponse(
            id=user["id"], 
            name=user["name"], 
            email=user["email"],
            wallet_balance=user.get("wallet_balance", 0.0)
        )
    )


# --- WALLET ROUTES ---
@api_router.get("/wallet")
async def get_wallet(current_user: dict = Depends(get_current_user)):
    user = await db.users.find_one({"id": current_user["id"]})
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    return {"balance": user.get("wallet_balance", 0.0)}

@api_router.post("/wallet/adjust")
async def adjust_wallet(adjustment: WalletAdjust, current_user: dict = Depends(get_current_user)):
    user = await db.users.find_one({"id": current_user["id"]})
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    
    current_balance = user.get("wallet_balance", 0.0)
    
    if adjustment.type == "add":
        new_balance = current_balance + adjustment.amount
    else:
        new_balance = current_balance - adjustment.amount
    
    await db.users.update_one(
        {"id": current_user["id"]},
        {"$set": {"wallet_balance": new_balance}}
    )
    
    return {"balance": new_balance}


# --- TRANSACTION ROUTES ---
@api_router.get("/transactions", response_model=List[Transaction])
async def get_transactions(current_user: dict = Depends(get_current_user)):
    transactions = await db.transactions.find({"user_id": current_user["id"]}, {"_id": 0}).to_list(1000)
    # Also get old expenses for backward compatibility
    expenses = await db.expenses.find({"user_id": current_user["id"]}, {"_id": 0}).to_list(1000)
    
    # Merge and add type if missing
    all_transactions = transactions + expenses
    for t in all_transactions:
        if "type" not in t:
            t["type"] = "expense"
    
    return all_transactions

@api_router.post("/transactions", response_model=Transaction)
async def create_transaction(
    amount: float = Form(...),
    date: str = Form(...),
    category: str = Form(...),
    method: str = Form(...),
    note: Optional[str] = Form(""),
    type: str = Form("expense"),
    receipt: Optional[UploadFile] = File(None),
    current_user: dict = Depends(get_current_user)
):
    receipt_url = None
    if receipt:
        file_ext = receipt.filename.split('.')[-1]
        file_name = f"{uuid.uuid4()}.{file_ext}"
        file_path = UPLOADS_DIR / file_name
        with open(file_path, "wb") as buffer:
            shutil.copyfileobj(receipt.file, buffer)
        receipt_url = f"/uploads/{file_name}"
    
    transaction = Transaction(
        user_id=current_user["id"],
        amount=amount,
        date=date,
        category=category,
        method=method,
        note=note,
        receipt_url=receipt_url,
        type=type
    )
    
    # Update wallet balance
    user = await db.users.find_one({"id": current_user["id"]})
    current_balance = user.get("wallet_balance", 0.0)
    
    if type == "income":
        new_balance = current_balance + amount
    else:  # expense
        new_balance = current_balance - amount
    
    await db.users.update_one(
        {"id": current_user["id"]},
        {"$set": {"wallet_balance": new_balance}}
    )
    
    transaction_dict = transaction.model_dump()
    transaction_dict['created_at'] = transaction_dict['created_at'].isoformat()
    await db.transactions.insert_one(transaction_dict)
    
    return transaction

@api_router.put("/transactions/{transaction_id}", response_model=Transaction)
async def update_transaction(
    transaction_id: str,
    transaction_data: TransactionUpdate,
    current_user: dict = Depends(get_current_user)
):
    transaction = await db.transactions.find_one({"id": transaction_id, "user_id": current_user["id"]})
    if not transaction:
        # Check old expenses collection
        transaction = await db.expenses.find_one({"id": transaction_id, "user_id": current_user["id"]})
        if not transaction:
            raise HTTPException(status_code=404, detail="Transaction not found")
    
    # If amount changed, adjust wallet
    if transaction_data.amount is not None and transaction_data.amount != transaction["amount"]:
        old_amount = transaction["amount"]
        new_amount = transaction_data.amount
        transaction_type = transaction.get("type", "expense")
        
        user = await db.users.find_one({"id": current_user["id"]})
        current_balance = user.get("wallet_balance", 0.0)
        
        # Reverse old transaction
        if transaction_type == "income":
            current_balance -= old_amount
        else:
            current_balance += old_amount
        
        # Apply new transaction
        if transaction_type == "income":
            current_balance += new_amount
        else:
            current_balance -= new_amount
        
        await db.users.update_one(
            {"id": current_user["id"]},
            {"$set": {"wallet_balance": current_balance}}
        )
    
    update_data = {k: v for k, v in transaction_data.model_dump().items() if v is not None}
    if update_data:
        await db.transactions.update_one({"id": transaction_id}, {"$set": update_data})
        await db.expenses.update_one({"id": transaction_id}, {"$set": update_data})
    
    updated_transaction = await db.transactions.find_one({"id": transaction_id}, {"_id": 0})
    if not updated_transaction:
        updated_transaction = await db.expenses.find_one({"id": transaction_id}, {"_id": 0})
    
    return updated_transaction

@api_router.delete("/transactions/{transaction_id}")
async def delete_transaction(transaction_id: str, current_user: dict = Depends(get_current_user)):
    transaction = await db.transactions.find_one({"id": transaction_id, "user_id": current_user["id"]})
    if not transaction:
        transaction = await db.expenses.find_one({"id": transaction_id, "user_id": current_user["id"]})
        if not transaction:
            raise HTTPException(status_code=404, detail="Transaction not found")
    
    # Reverse wallet transaction
    user = await db.users.find_one({"id": current_user["id"]})
    current_balance = user.get("wallet_balance", 0.0)
    
    transaction_type = transaction.get("type", "expense")
    amount = transaction["amount"]
    
    if transaction_type == "income":
        new_balance = current_balance - amount
    else:
        new_balance = current_balance + amount
    
    await db.users.update_one(
        {"id": current_user["id"]},
        {"$set": {"wallet_balance": new_balance}}
    )
    
    result = await db.transactions.delete_one({"id": transaction_id, "user_id": current_user["id"]})
    if result.deleted_count == 0:
        await db.expenses.delete_one({"id": transaction_id, "user_id": current_user["id"]})
    
    return {"message": "Transaction deleted successfully"}


# --- LEGACY EXPENSE ENDPOINTS ---
@api_router.get("/expenses", response_model=List[Transaction])
async def get_expenses(current_user: dict = Depends(get_current_user)):
    return await get_transactions(current_user)

@api_router.post("/expenses", response_model=Transaction)
async def create_expense(
    amount: float = Form(...),
    date: str = Form(...),
    category: str = Form(...),
    method: str = Form(...),
    note: Optional[str] = Form(""),
    receipt: Optional[UploadFile] = File(None),
    current_user: dict = Depends(get_current_user)
):
    return await create_transaction(amount, date, category, method, note, "expense", receipt, current_user)


# --- CATEGORY ROUTES ---
@api_router.get("/categories", response_model=List[Category])
async def get_categories(current_user: dict = Depends(get_current_user)):
    categories = await db.categories.find({"user_id": current_user["id"]}, {"_id": 0}).to_list(1000)
    return categories

@api_router.post("/categories", response_model=Category)
async def create_category(
    category_data: CategoryCreate,
    current_user: dict = Depends(get_current_user)
):
    category = Category(user_id=current_user["id"], **category_data.model_dump())
    category_dict = category.model_dump()
    category_dict['created_at'] = category_dict['created_at'].isoformat()
    await db.categories.insert_one(category_dict)
    return category

@api_router.delete("/categories/{category_id}")
async def delete_category(category_id: str, current_user: dict = Depends(get_current_user)):
    result = await db.categories.delete_one({"id": category_id, "user_id": current_user["id"]})
    if result.deleted_count == 0:
        raise HTTPException(status_code=404, detail="Category not found")
    return {"message": "Category deleted successfully"}


# --- BUDGET ROUTES ---
@api_router.get("/budgets", response_model=List[Budget])
async def get_budgets(current_user: dict = Depends(get_current_user)):
    budgets = await db.budgets.find({"user_id": current_user["id"]}, {"_id": 0}).to_list(1000)
    return budgets

@api_router.post("/budgets", response_model=Budget)
async def create_budget(
    budget_data: BudgetCreate,
    current_user: dict = Depends(get_current_user)
):
    budget = Budget(user_id=current_user["id"], **budget_data.model_dump())
    budget_dict = budget.model_dump()
    budget_dict['created_at'] = budget_dict['created_at'].isoformat()
    await db.budgets.insert_one(budget_dict)
    return budget

@api_router.delete("/budgets/{budget_id}")
async def delete_budget(budget_id: str, current_user: dict = Depends(get_current_user)):
    result = await db.budgets.delete_one({"id": budget_id, "user_id": current_user["id"]})
    if result.deleted_count == 0:
        raise HTTPException(status_code=404, detail="Budget not found")
    return {"message": "Budget deleted successfully"}


# --- ANALYTICS ROUTES ---
@api_router.get("/analytics/overview", response_model=OverviewStats)
async def get_overview(current_user: dict = Depends(get_current_user)):
    user = await db.users.find_one({"id": current_user["id"]})
    wallet_balance = user.get("wallet_balance", 0.0)
    
    transactions = await db.transactions.find({"user_id": current_user["id"]}).to_list(1000)
    expenses = await db.expenses.find({"user_id": current_user["id"]}).to_list(1000)
    all_transactions = transactions + expenses
    
    budgets = await db.budgets.find({"user_id": current_user["id"]}).to_list(1000)
    
    # Calculate monthly spending and income (current month)
    now = datetime.now(timezone.utc)
    current_month = now.strftime("%Y-%m")
    monthly_transactions = [t for t in all_transactions if t["date"].startswith(current_month)]
    
    monthly_spending = sum(t["amount"] for t in monthly_transactions if t.get("type", "expense") == "expense")
    monthly_income = sum(t["amount"] for t in monthly_transactions if t.get("type", "expense") == "income")
    
    return OverviewStats(
        wallet_balance=wallet_balance,
        monthly_income=monthly_income,
        monthly_spending=monthly_spending,
        active_budgets=len(budgets),
        upcoming_bills=0
    )

@api_router.get("/analytics/monthly")
async def get_monthly_analytics(current_user: dict = Depends(get_current_user)):
    transactions = await db.transactions.find({"user_id": current_user["id"]}).to_list(1000)
    expenses = await db.expenses.find({"user_id": current_user["id"]}).to_list(1000)
    all_transactions = transactions + expenses
    
    # Group by category
    category_spending = {}
    category_income = {}
    
    for t in all_transactions:
        cat = t["category"]
        amount = t["amount"]
        trans_type = t.get("type", "expense")
        
        if trans_type == "expense":
            if cat not in category_spending:
                category_spending[cat] = 0
            category_spending[cat] += amount
        else:
            if cat not in category_income:
                category_income[cat] = 0
            category_income[cat] += amount
    
    # Group by month
    monthly_data = {}
    for t in all_transactions:
        month = t["date"][:7]  # YYYY-MM
        if month not in monthly_data:
            monthly_data[month] = {"expense": 0, "income": 0}
        
        trans_type = t.get("type", "expense")
        monthly_data[month][trans_type] += t["amount"]
    
    return {
        "by_category": {
            "expense": category_spending,
            "income": category_income
        },
        "by_month": monthly_data
    }


# --- AI ROUTES ---
@api_router.post("/ai/categorize")
async def ai_categorize(request: AICategorizeRequest, current_user: dict = Depends(get_current_user)):
    """AI-powered transaction categorization"""
    try:
        result = await ai_service.categorize_transaction(
            text=request.text,
            amount=request.amount,
            date=request.date
        )
        return result
    except Exception as e:
        logging.error(f"AI categorize error: {str(e)}")
        # Fallback
        return ai_service._fallback_categorize(request.text, request.amount)

@api_router.post("/ai/suggest-expense")
async def ai_suggest_expense(request: AISuggestExpenseRequest, current_user: dict = Depends(get_current_user)):
    """AI-powered expense suggestions"""
    try:
        result = await ai_service.suggest_expense(
            text=request.text,
            amount=request.amount
        )
        return result
    except Exception as e:
        logging.error(f"AI suggest expense error: {str(e)}")
        # Fallback
        fallback = ai_service._fallback_categorize(request.text, request.amount)
        return {
            "category": fallback["category"],
            "method": ai_service._fallback_payment_method(request.text),
            "suggested_note": f"Transaction: {request.text[:50]}",
            "confidence": 0.6,
            "fallback": True
        }

@api_router.post("/ai/suggest-budget")
async def ai_suggest_budget(request: AISuggestBudgetRequest, current_user: dict = Depends(get_current_user)):
    """AI-powered budget suggestions"""
    try:
        # Get historical transactions
        transactions = await db.transactions.find({"user_id": current_user["id"]}).to_list(1000)
        expenses_legacy = await db.expenses.find({"user_id": current_user["id"]}).to_list(1000)
        all_transactions = transactions + expenses_legacy
        
        result = await ai_service.suggest_budget(
            transactions=all_transactions,
            months=request.months
        )
        return result
    except Exception as e:
        logging.error(f"AI suggest budget error: {str(e)}")
        return {"suggestions": [], "error": "AI temporarily unavailable", "fallback": True}

@api_router.post("/ai/search")
async def ai_search(request: AISearchRequest, current_user: dict = Depends(get_current_user)):
    """Natural language search"""
    try:
        # Get all transactions
        transactions = await db.transactions.find({"user_id": current_user["id"]}, {"_id": 0}).to_list(1000)
        expenses_legacy = await db.expenses.find({"user_id": current_user["id"]}, {"_id": 0}).to_list(1000)
        all_transactions = transactions + expenses_legacy
        
        # Ensure type field
        for t in all_transactions:
            if "type" not in t:
                t["type"] = "expense"
        
        result = await ai_service.natural_language_search(
            query=request.query,
            transactions=all_transactions
        )
        return result
    except Exception as e:
        logging.error(f"AI search error: {str(e)}")
        return {"filters": {}, "results": [], "query_summary": "Search unavailable", "error": str(e)}

@api_router.post("/ai/insights")
async def ai_insights(request: AIInsightsRequest, current_user: dict = Depends(get_current_user)):
    """AI-generated insights"""
    try:
        # Get transactions in range
        transactions = await db.transactions.find({
            "user_id": current_user["id"],
            "date": {"$gte": request.range["from"], "$lte": request.range["to"]}
        }, {"_id": 0}).to_list(1000)
        
        expenses_legacy = await db.expenses.find({
            "user_id": current_user["id"],
            "date": {"$gte": request.range["from"], "$lte": request.range["to"]}
        }, {"_id": 0}).to_list(1000)
        
        all_transactions = transactions + expenses_legacy
        
        # Ensure type field
        for t in all_transactions:
            if "type" not in t:
                t["type"] = "expense"
        
        result = await ai_service.generate_insights(
            transactions=all_transactions,
            date_range=request.range
        )
        return result
    except Exception as e:
        logging.error(f"AI insights error: {str(e)}")
        return {"summary": "Insights unavailable", "highlights": [], "error": str(e)}

@api_router.post("/ai/forecast")
async def ai_forecast(request: AIForecastRequest, current_user: dict = Depends(get_current_user)):
    """AI spending forecast"""
    try:
        # Get historical transactions
        transactions = await db.transactions.find({"user_id": current_user["id"]}, {"_id": 0}).to_list(1000)
        expenses_legacy = await db.expenses.find({"user_id": current_user["id"]}, {"_id": 0}).to_list(1000)
        all_transactions = transactions + expenses_legacy
        
        # Ensure type field
        for t in all_transactions:
            if "type" not in t:
                t["type"] = "expense"
        
        result = await ai_service.forecast_spending(
            transactions=all_transactions,
            months=request.months
        )
        return result
    except Exception as e:
        logging.error(f"AI forecast error: {str(e)}")
        return {
            "forecastByMonth": [],
            "byCategory": [],
            "safeToSpendToday": 0,
            "error": str(e)
        }

# Include the router
app.include_router(api_router)

# CORS Middleware
app.add_middleware(
    CORSMiddleware,
    allow_credentials=True,
    allow_origins=os.environ.get('CORS_ORIGINS', '*').split(','),
    allow_methods=["*"],
    allow_headers=["*"],
)

# Logging configuration
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Note: No need for @app.on_event("shutdown") anymore because we use lifespan!