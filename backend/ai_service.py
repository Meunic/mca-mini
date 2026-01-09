"""
AI Service Module for SmartExpense
Provides AI-powered features using the new Google GenAI SDK
"""

import os
import json
import logging
from google import genai
from typing import Dict, List, Optional, Any
from dotenv import load_dotenv

# Load environment variables
load_dotenv()

# Configuration
GEMINI_API_KEY = os.environ.get('GEMINI_API_KEY')
AI_MODEL = "gemini-1.5-flash"

logger = logging.getLogger(__name__)

# Initialize Client
client = None
if GEMINI_API_KEY:
    try:
        client = genai.Client(api_key=GEMINI_API_KEY)
    except Exception as e:
        logger.error(f"Failed to initialize Gemini client: {e}")

# Fallback keyword mappings
CATEGORY_KEYWORDS = {
    "Food & Dining": ["swiggy", "zomato", "food", "restaurant", "cafe", "dinner", "lunch", "breakfast", "pizza", "burger"],
    "Transportation": ["uber", "ola", "taxi", "metro", "bus", "train", "petrol", "fuel", "parking"],
    "Shopping": ["amazon", "flipkart", "myntra", "shop", "mall", "clothing", "fashion"],
    "Entertainment": ["netflix", "movie", "cinema", "concert", "game", "spotify", "prime"],
    "Bills & Utilities": ["electricity", "water", "gas", "internet", "mobile", "phone", "bill"],
    "Health": ["hospital", "doctor", "medicine", "pharmacy", "clinic", "health"],
    "Income": ["salary", "credited", "payment received", "income", "refund"],
}

PAYMENT_METHOD_KEYWORDS = {
    "UPI": ["upi", "paytm", "phonepe", "gpay", "google pay"],
    "Credit Card": ["credit", "card"],
    "Debit Card": ["debit", "card"],
    "Cash": ["cash", "atm"],
}

class AIService:
    """AI Service using the new google-genai library"""
    
    async def _call_ai(self, system_message: str, user_message: str) -> Optional[str]:
        if not client:
            logger.warning("Gemini client not initialized. Using fallbacks.")
            return None

        try:
            # New SDK usage
            response = await client.aio.models.generate_content(
                model=AI_MODEL,
                contents=user_message,
                config=genai.types.GenerateContentConfig(
                    system_instruction=system_message,
                    response_mime_type="application/json"
                )
            )
            return response.text
        except Exception as e:
            logger.error(f"Gemini AI call error: {str(e)}")
            return None
    
    def _extract_json_from_response(self, response: str) -> Optional[Dict]:
        if not response: return None
        try:
            return json.loads(response)
        except json.JSONDecodeError:
            cleaned = response.replace("```json", "").replace("```", "").strip()
            try: return json.loads(cleaned)
            except: return None

    # --- Fallback Methods (Same as before) ---
    def _fallback_categorize(self, text: str, amount: float = None) -> Dict[str, Any]:
        text_lower = text.lower()
        for category, keywords in CATEGORY_KEYWORDS.items():
            if any(keyword in text_lower for keyword in keywords):
                return {"category": category, "confidence": 0.7, "fallback": True}
        if amount and amount > 10000:
            return {"category": "Bills & Utilities", "confidence": 0.5, "fallback": True}
        return {"category": "Other", "confidence": 0.3, "fallback": True}
    
    def _fallback_payment_method(self, text: str) -> str:
        text_lower = text.lower()
        for method, keywords in PAYMENT_METHOD_KEYWORDS.items():
            if any(keyword in text_lower for keyword in keywords):
                return method
        return "UPI"

    # --- AI Feature Methods (Same logic, updated calls) ---
    async def categorize_transaction(self, text: str, amount: Optional[float] = None, date: Optional[str] = None) -> Dict[str, Any]:
        system_message = """Map to: Food & Dining, Transportation, Shopping, Entertainment, Bills & Utilities, Health, Income, Other.
Return JSON: {"category": "Name", "confidence": 0.9}"""
        user_message = f"Transaction: {text}, Amount: {amount}"
        
        response = await self._call_ai(system_message, user_message)
        result = self._extract_json_from_response(response)
        if result and "category" in result:
            result["fallback"] = False
            return result
        return self._fallback_categorize(text, amount)

    async def suggest_expense(self, text: str, amount: Optional[float] = None) -> Dict[str, Any]:
        system_message = """Suggest category, payment method (Cash, UPI, Card), and note.
Return JSON: {"category": "", "method": "", "suggested_note": "", "confidence": 0.9}"""
        user_message = f"Transaction: {text}, Amount: {amount}"
        
        response = await self._call_ai(system_message, user_message)
        result = self._extract_json_from_response(response)
        if result and "category" in result:
            result["fallback"] = False
            return result
        fallback = self._fallback_categorize(text, amount)
        return {"category": fallback["category"], "method": "UPI", "suggested_note": text, "confidence": 0.6, "fallback": True}

    async def suggest_budget(self, transactions: List[Dict], months: int = 3) -> Dict[str, Any]:
        cat_spend = {}
        for t in transactions:
            if t.get("type") == "expense":
                c = t.get("category", "Other")
                cat_spend[c] = cat_spend.get(c, 0) + t.get("amount", 0)
        
        system_message = """Suggest monthly budgets. JSON: {"suggestions": [{"category": "", "suggestedMonthlyBudget": 0, "rationale": ""}]}"""
        user_message = f"Spending ({months} months): {json.dumps(cat_spend)} INR"
        
        response = await self._call_ai(system_message, user_message)
        result = self._extract_json_from_response(response)
        return result if result else {"suggestions": [], "fallback": True}

    async def natural_language_search(self, query: str, transactions: List[Dict]) -> Dict[str, Any]:
        system_message = """Extract filters. JSON: {"dateRange":{"from":"YYYY-MM-DD","to":"YYYY-MM-DD"},"categories":[],"minAmount":0,"maxAmount":0,"methods":[],"type":""}"""
        response = await self._call_ai(system_message, f"Query: {query}")
        filters = self._extract_json_from_response(response) or {}
        
        results = transactions.copy()
        if filters.get("minAmount"): results = [t for t in results if t.get("amount", 0) >= filters["minAmount"]]
        if filters.get("maxAmount"): results = [t for t in results if t.get("amount", 0) <= filters["maxAmount"]]
        if filters.get("categories"): results = [t for t in results if t.get("category") in filters["categories"]]
        
        return {"filters": filters, "results": results, "query_summary": "Search results", "fallback": False}

    async def generate_insights(self, transactions: List[Dict], date_range: Dict) -> Dict[str, Any]:
        # Placeholder for simple insight generation
        return {"summary": "Insights generated", "highlights": [], "recommendations": [], "fallback": True}

    async def forecast_spending(self, transactions: List[Dict], months: int = 1) -> Dict[str, Any]:
        return {"forecastByMonth": [], "fallback": True}

ai_service = AIService()