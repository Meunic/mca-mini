# SmartExpense AI Features Documentation

## Overview

SmartExpense now includes comprehensive AI-powered features to make expense management smarter, faster, and more insightful. All features use OpenAI GPT-4o-mini with robust fallback mechanisms for reliability.

## Features Implemented

### 1. Auto-Categorization for Transactions
**Endpoint**: `POST /api/ai/categorize`

**Description**: Automatically categorizes transactions based on text, amount, and date.

**Request**:
```json
{
  "text": "Ordered food on Swiggy — paid ₹240",
  "amount": 240,
  "date": "2025-01-15"
}
```

**Response**:
```json
{
  "category": "Food & Dining",
  "confidence": 0.92,
  "fallback": false
}
```

**Fallback Logic**: Keyword-based categorization using predefined merchant/keyword mappings.

---

### 2. AI Expense Suggestions
**Endpoint**: `POST /api/ai/suggest-expense`

**Description**: Suggests category, payment method, and note for a transaction.

**Request**:
```json
{
  "text": "Uber ride to airport",
  "amount": 450
}
```

**Response**:
```json
{
  "category": "Transportation",
  "method": "UPI",
  "suggested_note": "Uber ride — airport commute",
  "confidence": 0.88,
  "fallback": false
}
```

**UI Integration**:
- "AI Suggest" button in Add Expense modal
- Auto-fills category, payment method, and note
- Shows confidence badge
- User can override suggestions

**Fallback Logic**: Rule-based category and payment method detection.

---

### 3. AI Budget Suggestions
**Endpoint**: `POST /api/ai/suggest-budget`

**Description**: Analyzes historical spending and recommends monthly budgets.

**Request**:
```json
{
  "months": 3,
  "categories": ["Food & Dining", "Transportation"]
}
```

**Response**:
```json
{
  "suggestions": [
    {
      "category": "Food & Dining",
      "suggestedMonthlyBudget": 6000,
      "rationale": "Based on ₹5,000 average spending + 20% buffer for occasional dining out"
    },
    {
      "category": "Transportation",
      "suggestedMonthlyBudget": 2400,
      "rationale": "Consistent ₹2,000/month pattern with slight increase for inflation"
    }
  ],
  "fallback": false
}
```

**UI Integration**:
- "AI Budget Suggestions" button on dashboard
- Modal displays recommendations with rationale
- One-click "Apply" to create budgets
- Shows historical analysis period

**Fallback Logic**: Simple average × 1.2 safety factor.

---

### 4. Natural Language Search
**Endpoint**: `POST /api/ai/search`

**Description**: Converts natural language queries into structured filters and returns matching transactions.

**Request**:
```json
{
  "query": "Show UPI payments above ₹500 last month"
}
```

**Response**:
```json
{
  "filters": {
    "minAmount": 500,
    "methods": ["UPI"],
    "dateRange": {
      "from": "2024-12-01",
      "to": "2024-12-31"
    }
  },
  "results": [
    {
      "id": "...",
      "amount": 750,
      "method": "UPI",
      "category": "Shopping",
      "date": "2024-12-15",
      "note": "Online purchase"
    }
  ],
  "query_summary": "Found 12 transactions matching: amount ≥ ₹500 | methods: UPI",
  "fallback": false
}
```

**Supported Queries**:
- Amount filters: "above ₹500", "less than ₹1000", "between ₹100 and ₹500"
- Date ranges: "last month", "this week", "last 7 days", "in January"
- Categories: "food expenses", "transportation", "bills"
- Payment methods: "UPI payments", "cash transactions", "card purchases"
- Types: "income", "expenses"

**UI Integration**:
- Dedicated search card with input field
- Real-time query processing
- Shows parsed filters summary
- Displays matching transaction count
- Filters transaction list dynamically

**Fallback Logic**: Regex-based extraction of amounts, dates, and keywords.

---

### 5. AI Insights & Summaries
**Endpoint**: `POST /api/ai/insights`

**Description**: Generates plain-language financial insights for a date range.

**Request**:
```json
{
  "range": {
    "from": "2024-12-01",
    "to": "2024-12-31"
  },
  "options": {
    "topN": 3
  }
}
```

**Response**:
```json
{
  "summary": "In December, you spent ₹18,500 against an income of ₹50,000, maintaining a healthy 63% savings rate. Your top spending was on Food & Dining (₹6,200), followed by Transportation (₹4,100). Notable spike in entertainment spending (2x average) suggests holiday season activities.",
  "highlights": [
    {
      "title": "Top Spending Category",
      "detail": "Food & Dining: ₹6,200 (33.5% of total expenses)"
    },
    {
      "title": "Insight",
      "detail": "Entertainment spending doubled compared to previous months"
    },
    {
      "title": "Health Expenses",
      "detail": "₹2,300 (below target — good control)"
    }
  ],
  "recommendations": [
    "Consider setting a monthly budget of ₹7,000 for Food & Dining",
    "Entertainment spending spike is temporary — monitor next month",
    "Excellent savings rate — consider investing surplus"
  ],
  "chartsData": {
    "categorySpending": {
      "Food & Dining": 6200,
      "Transportation": 4100,
      "Health": 2300
    }
  },
  "fallback": false
}
```

**UI Integration**:
- Date range selector (from/to)
- "Generate Insights" button
- Modal displays summary, highlights, and recommendations
- Visual breakdown of top categories
- Shareable summary

**Fallback Logic**: Statistical summary with category breakdowns.

---

### 6. Spending Forecast
**Endpoint**: `POST /api/ai/forecast`

**Description**: Predicts future spending based on historical patterns.

**Request**:
```json
{
  "months": 1,
  "categories": []
}
```

**Response**:
```json
{
  "forecastByMonth": [
    {
      "month": "2025-02",
      "total": 19200,
      "confidence": 0.75
    }
  ],
  "byCategory": [
    {
      "category": "Food & Dining",
      "predicted": 6500
    },
    {
      "category": "Transportation",
      "predicted": 4200
    },
    {
      "category": "Bills & Utilities",
      "predicted": 3500
    }
  ],
  "safeToSpendToday": 8400,
  "averageMonthlySpending": 18500,
  "fallback": true
}
```

**UI Integration**:
- "Spending Forecast" card on dashboard
- "Generate Forecast" button
- Displays:
  - **Safe to Spend Today**: Daily budget for remaining month days
  - **Next Month Prediction**: Total expected spending
  - **By Category**: Per-category predictions
- Visual indicators (badges, progress)
- Refresh forecast button

**Fallback Logic**: Moving average of recent 3 months with simple daily budget calculation.

---

## Technical Architecture

### Backend (`/app/backend/ai_service.py`)

**AIService Class**:
- Singleton instance for all AI operations
- Uses `emergentintegrations` library for LLM calls
- Structured JSON output with temperature 0.1 for determinism
- Automatic fallback on API failures

**Key Methods**:
```python
async def categorize_transaction(text, amount, date)
async def suggest_expense(text, amount)
async def suggest_budget(transactions, months)
async def natural_language_search(query, transactions)
async def generate_insights(transactions, date_range)
async def forecast_spending(transactions, months)
```

**Prompt Templates**:
- Carefully crafted system messages for structured output
- Context-specific user messages with examples
- JSON schema enforcement
- Validation and sanitization

### Frontend Components

**Location**: `/app/frontend/src/components/AIFeatures/`

1. **AISuggestButton**: AI suggestion trigger in Add Expense modal
2. **NaturalLanguageSearch**: Search card with query input
3. **BudgetSuggestions**: Budget recommendation modal
4. **AIInsights**: Insights generation with date picker
5. **SpendingForecast**: Forecast card with predictions

### Error Handling & Fallbacks

**Three-Layer Approach**:

1. **AI Layer**: Primary OpenAI GPT-4o-mini call
2. **Backend Fallback**: Rule-based logic if AI fails
3. **Frontend Fallback**: Graceful degradation with error messages

**Fallback Indicators**:
- All responses include `fallback: boolean` field
- UI shows "Using fallback rules" badge
- Confidence scores adjusted for fallback mode

---

## Configuration

### Environment Variables

```bash
# /app/backend/.env
EMERGENT_LLM_KEY=sk-emergent-1Ee39Fd5d15C0Fd87E
```

### Model Configuration

```python
# ai_service.py
AI_MODEL = "gpt-4o-mini"
AI_PROVIDER = "openai"
AI_TEMPERATURE = 0.1  # Low for deterministic outputs
```

---

## Usage Examples

### 1. Auto-Categorize While Adding Expense

```javascript
// When user adds expense without selecting category
const response = await axios.post('/api/ai/categorize', {
  text: expense.note,
  amount: expense.amount,
  date: expense.date
});

// Auto-fill category
expense.category = response.data.category;
```

### 2. Get AI Suggestions in Modal

User enters:
- Note: "Swiggy order for dinner"
- Amount: ₹450

AI suggests:
- Category: "Food & Dining"
- Method: "UPI"
- Note: "Swiggy food delivery — dinner"
- Confidence: 91%

User can accept or modify suggestions before saving.

### 3. Natural Language Search

User types: "show all food expenses above 500 rupees this month"

AI extracts:
- Category: Food & Dining
- Min Amount: ₹500
- Date Range: Current month

Returns matching transactions with summary.

### 4. Get Monthly Insights

User selects date range: Dec 1 - Dec 31

AI analyzes:
- 45 transactions
- ₹18,500 expenses
- ₹50,000 income
- Top 3 categories

Generates:
- Plain-language summary
- Key highlights
- Actionable recommendations

### 5. Forecast Next Month

AI analyzes:
- Last 3 months spending
- Category patterns
- Seasonal variations

Predicts:
- Total: ₹19,200
- By category breakdown
- Safe daily budget: ₹640

---

## Performance & Optimization

### Caching Strategy
- Repeated queries cached for 5 minutes
- Category mappings cached permanently
- Forecast results cached until new transaction

### Rate Limiting
- Max 10 AI calls per minute per user
- Fallback activates if limit exceeded
- Queue system for bulk operations

### Token Optimization
- Send aggregated stats instead of full transaction list
- Limit transaction history to last 1000 records
- Use compact JSON format in prompts

---

## Testing

### Backend Tests

```bash
# Test AI categorization
curl -X POST http://localhost:8001/api/ai/categorize \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"text": "Swiggy order", "amount": 250}'

# Expected: {"category": "Food & Dining", "confidence": 0.9, "fallback": false}
```

### Frontend Tests

**Test Scenarios**:
1. Click "AI Suggest" with note text → Form auto-fills
2. Enter natural language query → Results filter
3. Click "Generate Insights" → Modal shows summary
4. Click "Generate Forecast" → Predictions display
5. Network failure → Fallback indicators show

### Sample Test Data

```javascript
const testTransactions = [
  { text: "Paid ₹250 to Swiggy for dinner", amount: 250, expected: "Food & Dining" },
  { text: "Uber ride 18-11-2025 ₹375", amount: 375, expected: "Transportation" },
  { text: "Salary credited by ABC Corp ₹50,000", amount: 50000, expected: "Income" },
  { text: "Netflix subscription", amount: 199, expected: "Entertainment" },
  { text: "Electricity bill payment", amount: 1500, expected: "Bills & Utilities" }
];
```

---

## Security & Privacy

### Data Protection
- No transaction data stored by AI provider
- Prompt sanitization to remove PII
- On-device fallback doesn't send data externally

### User Control
- AI features opt-in by default
- User can disable AI and use only fallback rules
- All AI suggestions are editable before saving

### Rate Limiting
- Per-user API call limits
- Abuse detection and throttling
- Automatic fallback under heavy load

---

## Troubleshooting

### "AI temporarily unavailable"
**Cause**: OpenAI API failure or rate limit
**Solution**: Automatic fallback to rule-based logic
**Action**: Feature continues working with lower accuracy

### Low Confidence Scores (<50%)
**Cause**: Ambiguous transaction text or insufficient context
**Solution**: Provide more details in note field or amount
**Action**: User can override AI suggestions

### Incorrect Categorization
**Cause**: Novel transaction pattern not in training data
**Solution**: User edits category → System learns (future feature)
**Action**: Override and save correct category

### Forecast Seems Off
**Cause**: Insufficient historical data (<2 months)
**Solution**: Use simple average fallback
**Action**: Accumulate more transaction history

---

## Future Enhancements

### Phase 2 Features
1. **Learning from Corrections**: Train on user overrides
2. **Recurring Transaction Detection**: Auto-identify subscriptions
3. **Anomaly Detection**: Alert on unusual spending
4. **Smart Notifications**: Proactive budget warnings
5. **Voice Input**: Speech-to-expense with AI parsing
6. **Multi-language**: Support Hindi, Tamil, etc.

### Phase 3 Features
1. **Collaborative Budgeting**: Family/shared expense AI
2. **Investment Suggestions**: Surplus fund recommendations
3. **Bill Negotiation**: Auto-identify overcharges
4. **Tax Optimization**: Deduction suggestions

---

## API Reference Summary

| Endpoint | Method | Description | Fallback |
|----------|--------|-------------|----------|
| `/api/ai/categorize` | POST | Auto-categorize transaction | ✅ Keyword matching |
| `/api/ai/suggest-expense` | POST | Suggest category, method, note | ✅ Rule-based |
| `/api/ai/suggest-budget` | POST | Recommend budgets | ✅ Average × 1.2 |
| `/api/ai/search` | POST | Natural language search | ✅ Regex parsing |
| `/api/ai/insights` | POST | Generate insights | ✅ Statistical summary |
| `/api/ai/forecast` | POST | Predict spending | ✅ Moving average |

---

## Cost Estimation

### Per-User Monthly Usage
- **Categorizations**: ~60 (2/day) → $0.02
- **Suggestions**: ~30 (1/day) → $0.015
- **Budgets**: ~4 (1/week) → $0.01
- **Search**: ~20 (varies) → $0.01
- **Insights**: ~4 (1/week) → $0.02
- **Forecast**: ~8 (2/week) → $0.02

**Total**: ~$0.095 per user per month

**With Emergent LLM Key**: All costs covered by platform

---

## Monitoring & Logging

### Metrics Tracked
- AI call success/failure rate
- Average response time
- Fallback activation frequency
- User satisfaction (implicit: override rate)
- Confidence score distribution

### Debug Mode
Set `window.DEBUG = true` in browser console to see:
- Full AI responses
- Prompt texts
- Fallback triggers
- Performance metrics

---

## Support & Maintenance

### Common Issues
1. **High latency**: Check network connection, use fallback
2. **Incorrect results**: Provide feedback (future feature)
3. **Missing features**: Ensure latest version deployed

### Contact
For issues or feature requests, check:
- GitHub Issues: [repository]
- Documentation: This file
- Backend logs: `/var/log/supervisor/backend.*.log`

---

**Version**: 1.0.0  
**Last Updated**: January 2025  
**AI Model**: OpenAI GPT-4o-mini  
**Provider**: Emergent Integrations Library
