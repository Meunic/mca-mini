import requests
import sys
import json
from datetime import datetime, timedelta
import uuid

class SmartExpenseAPITester:
    def __init__(self, base_url="https://smartexpense-6.preview.emergentagent.com/api"):
        self.base_url = base_url
        self.token = None
        self.user_id = None
        self.tests_run = 0
        self.tests_passed = 0
        self.test_results = []
        
        # Test data
        self.test_user_email = f"test_{uuid.uuid4().hex[:8]}@example.com"
        self.test_user_password = "TestPass123!"
        self.test_user_name = "Test User"
        
        # Store created IDs for cleanup
        self.created_expense_ids = []
        self.created_category_ids = []
        self.created_budget_ids = []

    def log_test(self, name, success, details=""):
        """Log test result"""
        self.tests_run += 1
        if success:
            self.tests_passed += 1
            print(f"✅ {name}")
        else:
            print(f"❌ {name} - {details}")
        
        self.test_results.append({
            "test": name,
            "success": success,
            "details": details
        })

    def run_test(self, name, method, endpoint, expected_status, data=None, files=None):
        """Run a single API test"""
        url = f"{self.base_url}/{endpoint}"
        headers = {'Content-Type': 'application/json'}
        
        if self.token:
            headers['Authorization'] = f'Bearer {self.token}'
        
        try:
            if method == 'GET':
                response = requests.get(url, headers=headers)
            elif method == 'POST':
                if files:
                    # Remove Content-Type for file uploads
                    headers.pop('Content-Type', None)
                    response = requests.post(url, data=data, files=files, headers=headers)
                else:
                    response = requests.post(url, json=data, headers=headers)
            elif method == 'PUT':
                response = requests.put(url, json=data, headers=headers)
            elif method == 'DELETE':
                response = requests.delete(url, headers=headers)

            success = response.status_code == expected_status
            
            if success:
                try:
                    response_data = response.json()
                    self.log_test(name, True)
                    return True, response_data
                except:
                    self.log_test(name, True)
                    return True, {}
            else:
                error_msg = f"Expected {expected_status}, got {response.status_code}"
                try:
                    error_detail = response.json().get('detail', '')
                    if error_detail:
                        error_msg += f" - {error_detail}"
                except:
                    pass
                self.log_test(name, False, error_msg)
                return False, {}

        except Exception as e:
            self.log_test(name, False, f"Error: {str(e)}")
            return False, {}

    def test_user_registration(self):
        """Test user registration with wallet balance"""
        success, response = self.run_test(
            "User Registration",
            "POST",
            "auth/register",
            200,
            data={
                "name": self.test_user_name,
                "email": self.test_user_email,
                "password": self.test_user_password
            }
        )
        
        if success and 'token' in response:
            self.token = response['token']
            self.user_id = response['user']['id']
            # Check if user has starting wallet balance
            wallet_balance = response['user'].get('wallet_balance', 0)
            if wallet_balance == 10000.0:
                print(f"   ✅ Starting wallet balance: ₹{wallet_balance}")
            else:
                print(f"   ⚠️ Unexpected wallet balance: ₹{wallet_balance}")
            return True
        return False

    def test_user_login(self):
        """Test user login"""
        success, response = self.run_test(
            "User Login",
            "POST",
            "auth/login",
            200,
            data={
                "email": self.test_user_email,
                "password": self.test_user_password
            }
        )
        
        if success and 'token' in response:
            self.token = response['token']
            return True
        return False

    def test_get_categories(self):
        """Test getting categories (should have default categories)"""
        success, response = self.run_test(
            "Get Categories",
            "GET",
            "categories",
            200
        )
        
        if success and isinstance(response, list) and len(response) > 0:
            print(f"   Found {len(response)} default categories")
            return True
        return False

    def test_create_category(self):
        """Test creating a new category"""
        success, response = self.run_test(
            "Create Category",
            "POST",
            "categories",
            200,
            data={
                "name": "Test Category",
                "icon": "🧪",
                "color": "#ff6b6b"
            }
        )
        
        if success and 'id' in response:
            self.created_category_ids.append(response['id'])
            return True
        return False

    def test_wallet_endpoints(self):
        """Test wallet-related endpoints"""
        # Test get wallet balance
        success, response = self.run_test(
            "Get Wallet Balance",
            "GET",
            "wallet",
            200
        )
        
        if success and 'balance' in response:
            print(f"   Current wallet balance: ₹{response['balance']}")
            return True
        return False

    def test_create_expense(self):
        """Test creating an expense (debits wallet)"""
        # Use form data for expense creation (supports file uploads)
        url = f"{self.base_url}/transactions"
        headers = {'Authorization': f'Bearer {self.token}'}
        
        form_data = {
            "amount": "25.50",
            "date": datetime.now().strftime("%Y-%m-%d"),
            "category": "Food & Dining",
            "method": "UPI",
            "note": "Test expense",
            "type": "expense"
        }
        
        try:
            response = requests.post(url, data=form_data, headers=headers)
            success = response.status_code == 200
            
            if success:
                response_data = response.json()
                self.log_test("Create Expense", True)
                if 'id' in response_data:
                    self.created_expense_ids.append(response_data['id'])
                return True
            else:
                error_msg = f"Expected 200, got {response.status_code}"
                try:
                    error_detail = response.json().get('detail', '')
                    if error_detail:
                        error_msg += f" - {error_detail}"
                except:
                    pass
                self.log_test("Create Expense", False, error_msg)
                return False
        except Exception as e:
            self.log_test("Create Expense", False, f"Error: {str(e)}")
            return False

    def test_create_income(self):
        """Test creating income (credits wallet)"""
        url = f"{self.base_url}/transactions"
        headers = {'Authorization': f'Bearer {self.token}'}
        
        form_data = {
            "amount": "1000.00",
            "date": datetime.now().strftime("%Y-%m-%d"),
            "category": "Income",
            "method": "UPI",
            "note": "Test income",
            "type": "income"
        }
        
        try:
            response = requests.post(url, data=form_data, headers=headers)
            success = response.status_code == 200
            
            if success:
                response_data = response.json()
                self.log_test("Create Income", True)
                if 'id' in response_data:
                    self.created_expense_ids.append(response_data['id'])
                return True
            else:
                error_msg = f"Expected 200, got {response.status_code}"
                try:
                    error_detail = response.json().get('detail', '')
                    if error_detail:
                        error_msg += f" - {error_detail}"
                except:
                    pass
                self.log_test("Create Income", False, error_msg)
                return False
        except Exception as e:
            self.log_test("Create Income", False, f"Error: {str(e)}")
            return False

    def test_get_transactions(self):
        """Test getting transactions (both expenses and income)"""
        success, response = self.run_test(
            "Get Transactions",
            "GET",
            "transactions",
            200
        )
        
        if success and isinstance(response, list):
            print(f"   Found {len(response)} transactions")
            # Check transaction types
            income_count = len([t for t in response if t.get('type') == 'income'])
            expense_count = len([t for t in response if t.get('type') == 'expense'])
            print(f"   Income: {income_count}, Expenses: {expense_count}")
            return True
        return False

    def test_update_transaction(self):
        """Test updating a transaction"""
        if not self.created_expense_ids:
            self.log_test("Update Transaction", False, "No transaction to update")
            return False
            
        transaction_id = self.created_expense_ids[0]
        success, response = self.run_test(
            "Update Transaction",
            "PUT",
            f"transactions/{transaction_id}",
            200,
            data={
                "amount": 30.00,
                "note": "Updated test transaction"
            }
        )
        return success

    def test_create_budget(self):
        """Test creating a budget"""
        start_date = datetime.now().strftime("%Y-%m-%d")
        end_date = (datetime.now() + timedelta(days=30)).strftime("%Y-%m-%d")
        
        success, response = self.run_test(
            "Create Budget",
            "POST",
            "budgets",
            200,
            data={
                "category": "Food & Dining",
                "amount": 500.00,
                "period": "monthly",
                "start_date": start_date,
                "end_date": end_date
            }
        )
        
        if success and 'id' in response:
            self.created_budget_ids.append(response['id'])
            return True
        return False

    def test_get_budgets(self):
        """Test getting budgets"""
        success, response = self.run_test(
            "Get Budgets",
            "GET",
            "budgets",
            200
        )
        
        if success and isinstance(response, list):
            print(f"   Found {len(response)} budgets")
            return True
        return False

    def test_analytics_overview(self):
        """Test analytics overview with wallet balance"""
        success, response = self.run_test(
            "Analytics Overview",
            "GET",
            "analytics/overview",
            200
        )
        
        if success and 'wallet_balance' in response:
            print(f"   Wallet balance: ₹{response.get('wallet_balance', 0):.2f}")
            print(f"   Monthly income: ₹{response.get('monthly_income', 0):.2f}")
            print(f"   Monthly spending: ₹{response.get('monthly_spending', 0):.2f}")
            return True
        return False

    def test_analytics_monthly(self):
        """Test monthly analytics"""
        success, response = self.run_test(
            "Analytics Monthly",
            "GET",
            "analytics/monthly",
            200
        )
        
        if success and 'by_category' in response and 'by_month' in response:
            return True
        return False

    def test_ai_endpoints(self):
        """Test AI placeholder endpoints"""
        # Test AI categorize
        success1, _ = self.run_test(
            "AI Categorize",
            "POST",
            "ai/categorize",
            200,
            data={"description": "Coffee at Starbucks"}
        )
        
        # Test AI budget suggestions
        success2, _ = self.run_test(
            "AI Budget Suggestions",
            "POST",
            "ai/suggest-budget",
            200
        )
        
        return success1 and success2

    def test_delete_operations(self):
        """Test delete operations"""
        success_count = 0
        
        # Delete transactions
        for transaction_id in self.created_expense_ids:
            success, _ = self.run_test(
                f"Delete Transaction {transaction_id[:8]}",
                "DELETE",
                f"transactions/{transaction_id}",
                200
            )
            if success:
                success_count += 1
        
        # Delete budgets
        for budget_id in self.created_budget_ids:
            success, _ = self.run_test(
                f"Delete Budget {budget_id[:8]}",
                "DELETE",
                f"budgets/{budget_id}",
                200
            )
            if success:
                success_count += 1
        
        # Delete categories
        for category_id in self.created_category_ids:
            success, _ = self.run_test(
                f"Delete Category {category_id[:8]}",
                "DELETE",
                f"categories/{category_id}",
                200
            )
            if success:
                success_count += 1
        
        return success_count > 0

    def test_authentication_errors(self):
        """Test authentication error handling"""
        # Save current token
        original_token = self.token
        
        # Test with invalid token
        self.token = "invalid_token"
        success, _ = self.run_test(
            "Invalid Token Handling",
            "GET",
            "expenses",
            401
        )
        
        # Test with no token
        self.token = None
        success2, _ = self.run_test(
            "No Token Handling",
            "GET",
            "transactions",
            403  # FastAPI returns 403 for missing auth
        )
        
        # Restore token
        self.token = original_token
        
        return success and success2

    def run_all_tests(self):
        """Run all API tests"""
        print("🚀 Starting SmartExpense API Tests")
        print(f"📍 Testing against: {self.base_url}")
        print("=" * 50)
        
        # Authentication tests
        if not self.test_user_registration():
            print("❌ Registration failed, stopping tests")
            return False
        
        if not self.test_user_login():
            print("❌ Login failed, stopping tests")
            return False
        
        # Core functionality tests
        self.test_wallet_endpoints()
        self.test_get_categories()
        self.test_create_category()
        self.test_create_expense()
        self.test_create_income()
        self.test_get_transactions()
        self.test_update_transaction()
        self.test_create_budget()
        self.test_get_budgets()
        
        # Analytics tests
        self.test_analytics_overview()
        self.test_analytics_monthly()
        
        # AI endpoints
        self.test_ai_endpoints()
        
        # Error handling
        self.test_authentication_errors()
        
        # Cleanup
        self.test_delete_operations()
        
        # Print results
        print("=" * 50)
        print(f"📊 Tests completed: {self.tests_passed}/{self.tests_run} passed")
        
        if self.tests_passed == self.tests_run:
            print("🎉 All tests passed!")
            return True
        else:
            print(f"⚠️  {self.tests_run - self.tests_passed} tests failed")
            return False

def main():
    tester = SmartExpenseAPITester()
    success = tester.run_all_tests()
    
    # Save detailed results
    with open('/app/backend_test_results.json', 'w') as f:
        json.dump({
            'summary': {
                'total_tests': tester.tests_run,
                'passed_tests': tester.tests_passed,
                'success_rate': (tester.tests_passed / tester.tests_run * 100) if tester.tests_run > 0 else 0
            },
            'test_results': tester.test_results,
            'timestamp': datetime.now().isoformat()
        }, f, indent=2)
    
    return 0 if success else 1

if __name__ == "__main__":
    sys.exit(main())