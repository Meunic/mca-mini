# SmartExpense - Wallet Feature Documentation

## Overview

SmartExpense now includes a comprehensive Wallet Balance feature that allows users to track their monthly financial health with real-time income and expense management.

## Key Features

### 1. Wallet Balance
- **Starting Balance**: New users start with ₹ 10,000 in their wallet
- **Real-time Tracking**: Balance updates instantly with every transaction
- **Negative Balance Allowed**: Users can spend beyond their wallet balance (overdraft)
- **Visual Indicators**: 
  - Green for positive balance
  - Red for negative balance with overdraft warning

### 2. Add Money Feature
Users can add money to their wallet through multiple sources:
- **Payment Methods**: Cash, UPI, Debit Card, Credit Card
- **Transaction Details**: Amount, Date, Source, Optional Note, Optional Receipt
- **Wallet Credit**: Money is immediately added to the wallet balance

### 3. Expense Management
- **Wallet Debit**: All expenses are automatically deducted from wallet balance
- **Overdraft Support**: Expenses can be added even if they exceed current balance
- **Warning System**: Visual alerts when an expense would cause negative balance
- **Payment Methods**: Cash, UPI, Debit Card, Credit Card

### 4. Transaction Types
All transactions are categorized as:
- **Income**: Credits to the wallet (green indicator ↑)
- **Expense**: Debits from the wallet (red indicator ↓)

## Currency Formatting

### Indian Rupees (₹)
All amounts are displayed in Indian Rupee format:
- Symbol: ₹
- Locale: en-IN
- Format: ₹ 1,23,456.00

### Usage Example
```javascript
import { formatCurrency } from '@/utils/currency';

const amount = 12345.67;
console.log(formatCurrency(amount)); // ₹ 12,345.67
```

## Backend API Endpoints

### Wallet Endpoints
```
GET  /api/wallet              - Get current wallet balance
POST /api/wallet/adjust       - Adjust wallet balance (add/deduct)
```

### Transaction Endpoints
```
GET    /api/transactions          - Get all transactions (income & expense)
POST   /api/transactions          - Create new transaction
PUT    /api/transactions/:id      - Update transaction
DELETE /api/transactions/:id      - Delete transaction (reverses wallet impact)
```

### Transaction Data Model
```json
{
  "id": "uuid",
  "user_id": "uuid",
  "amount": 1500.00,
  "date": "2025-01-15",
  "category": "Food & Dining",
  "method": "UPI",
  "note": "Lunch with team",
  "receipt_url": "/uploads/receipt.jpg",
  "type": "expense"  // or "income"
}
```

## UI Components

### WalletCard
Displays wallet balance with Add Money and History buttons.

**Props:**
- `balance`: Current wallet balance (number)
- `onAddMoney`: Callback when Add Money is clicked
- `onViewHistory`: Callback when History is clicked

**Features:**
- Shows overdraft warning when balance is negative
- Color-coded balance display (green/red)
- Quick action buttons

### AddMoneyModal
Modal for adding money to the wallet.

**Props:**
- `open`: Boolean to control modal visibility
- `onClose`: Callback when modal is closed
- `onSubmit`: Callback with transaction data

**Fields:**
- Amount (₹)
- Date
- Source/Payment Method
- Note (optional)
- Receipt upload (optional)

### AddExpenseModal
Enhanced with wallet integration.

**Props:**
- `open`: Boolean to control modal visibility
- `onClose`: Callback when modal is closed
- `onSubmit`: Callback with transaction data
- `categories`: Array of categories
- `editingExpense`: Expense being edited (optional)
- `walletBalance`: Current wallet balance

**Features:**
- Shows current wallet balance
- Warns about overdraft before submission
- Allows expenses beyond balance
- Supports all payment methods

## Payment Methods

All transactions support these payment methods:
- **Cash**: Physical cash transactions
- **UPI**: Unified Payments Interface (most common in India)
- **Debit Card**: Direct bank account debit
- **Credit Card**: Credit-based purchases

## Overdraft Management

### Behavior
- Expenses can exceed wallet balance (no hard limit)
- Visual warning shown before adding expense
- Negative balance displayed in red
- Overdraft amount clearly indicated

### Example
If wallet balance is ₹ 5,000 and user adds expense of ₹ 8,000:
- New balance: ₹ -3,000
- Warning: "You are overdrawn by ₹ 3,000"
- Transaction still allowed

## Testing the Feature

### Manual Testing Steps

1. **Register New User**
   - Create account → Receive ₹ 10,000 starting balance
   - Check wallet card shows ₹ 10,000

2. **Add Money**
   - Click "Add Money" button
   - Enter amount: ₹ 5,000
   - Select method: UPI
   - Submit → Balance should be ₹ 15,000

3. **Add Expense**
   - Click "Add Expense"
   - Enter amount: ₹ 3,000
   - Category: Food & Dining
   - Method: UPI
   - Submit → Balance should be ₹ 12,000

4. **Test Overdraft**
   - Add expense: ₹ 15,000 (more than balance)
   - See warning about overdraft
   - Submit → Balance should be ₹ -3,000 (negative, shown in red)

5. **Check Transaction History**
   - View all transactions with type indicators
   - Income shows green arrow up (↑)
   - Expenses show red arrow down (↓)

## Best Practices

### For Developers
1. Always use `formatCurrency()` for displaying amounts
2. Check wallet balance before showing overdraft warnings
3. Update wallet balance after every transaction
4. Handle transaction failures with rollback
5. Use FormData for file uploads (receipts)

### For Users
1. Regularly add money to maintain positive balance
2. Use appropriate payment method for each transaction
3. Add notes to track transaction purposes
4. Monitor overdraft warnings
5. Review transaction history periodically

## Integration with Other Features

### Budgets
- Budgets track expenses only (not income)
- Wallet balance is independent of budget limits
- Budget warnings don't prevent transactions

### Categories
- Default "Income" category for money additions
- All other categories for expenses
- Categories have colors for visual distinction

### Analytics
- Charts show income vs expense trends
- Pie chart shows expense breakdown by category
- Line chart shows monthly income/expense comparison

## Future Enhancements

Potential features for future versions:
- Monthly wallet reset option
- Wallet balance alerts/notifications
- Automatic budget suggestions based on wallet balance
- Savings goals linked to wallet
- Recurring income/expense automation
- Multi-wallet support (different accounts)

## Troubleshooting

### Balance Not Updating
- Check browser console for API errors
- Verify backend is running (port 8001)
- Check MongoDB connection
- Refresh the page

### Negative Balance Issues
- This is expected behavior (overdraft allowed)
- Check if warning is displayed correctly
- Verify red color is applied

### Transaction Not Appearing
- Check if API call succeeded
- Verify transaction type is set correctly
- Refresh transaction list
- Check backend logs

## Support

For issues or questions:
1. Check browser console for errors
2. Review backend logs: `tail -f /var/log/supervisor/backend.*.log`
3. Verify MongoDB is running: `sudo supervisorctl status`
4. Check API responses in Network tab

---

**Last Updated**: January 2025  
**Version**: 1.0.0
