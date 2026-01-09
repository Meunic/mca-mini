package com.smartexpense.app.data.model

import com.google.gson.annotations.SerializedName

// --- Requests (These were missing!) ---

data class AICategorizeRequest(
    val text: String,
    val amount: Double? = null,
    val date: String? = null
)

data class AISearchRequest(
    val query: String
)

// New missing request classes
data class AIBudgetSuggestionRequest(
    val user_id: String? = null // Optional, backend handles current user
)

data class AIInsightsRequest(
    val range: String = "month" // Default to monthly insights
)

data class AIForecastRequest(
    val category: String? = null // Optional, forecast all if null
)


// --- Responses ---

data class AICategorizeResponse(
    val category: String,
    val confidence: Double,
    val fallback: Boolean = false
)

data class AIExpenseSuggestionResponse(
    val suggestions: List<ExpenseSuggestion>
)

data class ExpenseSuggestion(
    val category: String,
    val description: String,
    @SerializedName("estimated_amount")
    val estimatedAmount: Double
)

data class AISearchResponse(
    val results: List<Transaction>
)

data class AIInsightsResponse(
    val insights: List<String>
)

data class AIForecastResponse(
    @SerializedName("forecast_data")
    val forecastData: List<ForecastPoint>
)

data class ForecastPoint(
    val month: String,
    @SerializedName("predicted_spending")
    val predictedSpending: Double
)

data class AIBudgetSuggestionResponse(
    val suggestions: Map<String, Double>
)