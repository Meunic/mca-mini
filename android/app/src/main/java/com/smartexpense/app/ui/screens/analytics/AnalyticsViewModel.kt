package com.smartexpense.app.ui.screens.analytics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartexpense.app.data.repository.TransactionRepository
import com.smartexpense.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Define the State structure explicitly
data class AnalyticsState(
    val isLoading: Boolean = false,
    val spendingData: List<Double> = emptyList(), // <--- This fixes the error
    val totalSpent: String = "0.00",
    val averageDaily: String = "0.00",
    val error: String? = null
)

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    var state by mutableStateOf(AnalyticsState())
        private set

    init {
        loadAnalytics()
    }

    fun loadAnalytics() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            
            when (val result = repository.getTransactions()) {
                is Resource.Success -> {
                    val transactions = result.data ?: emptyList()
                    val expenses = transactions.filter { it.type == "expense" }

                    // 1. Calculate Total Spent
                    val total = expenses.sumOf { it.amount }

                    // 2. Calculate Daily Average (Simplified: Total / 30 days)
                    val avg = if (expenses.isNotEmpty()) total / 30 else 0.0

                    // 3. Prepare Chart Data
                    // We take the amounts of the last 10 expenses for the chart
                    // (In a real app, you would group these by date)
                    val chartData = expenses.take(10).map { it.amount }

                    state = state.copy(
                        isLoading = false,
                        spendingData = chartData, // Populating the list
                        totalSpent = String.format("%.2f", total),
                        averageDaily = String.format("%.2f", avg)
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message ?: "Failed to load analytics"
                    )
                }
                else -> {
                     state = state.copy(isLoading = false)
                }
            }
        }
    }
}