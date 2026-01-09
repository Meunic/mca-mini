package com.smartexpense.app.ui.screens.budget

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartexpense.app.data.model.AddBudgetRequest
import com.smartexpense.app.data.model.Budget
import com.smartexpense.app.data.repository.BudgetRepository
import com.smartexpense.app.data.repository.TransactionRepository
import com.smartexpense.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BudgetState(
    val budgets: List<Budget> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    var state by mutableStateOf(BudgetState())
        private set

    init {
        loadBudgets()
    }

    fun loadBudgets() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            
            // FIXED: Removed invalid call to 'syncTransactions'
            // We just fetch the latest budgets directly
            when (val result = budgetRepository.getBudgets()) {
                is Resource.Success -> {
                    state = state.copy(
                        budgets = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message ?: "Failed to load budgets",
                        isLoading = false
                    )
                }
                else -> state = state.copy(isLoading = false)
            }
        }
    }

    fun addBudget(category: String, amount: Double) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val request = AddBudgetRequest(category, amount, "monthly")
            
            when (val result = budgetRepository.addBudget(request)) {
                is Resource.Success -> {
                    loadBudgets() // Reload list on success
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message ?: "Failed to add budget",
                        isLoading = false
                    )
                }
                else -> state = state.copy(isLoading = false)
            }
        }
    }
    
    fun deleteBudget(id: String) {
        viewModelScope.launch {
            val result = budgetRepository.deleteBudget(id)
            if (result is Resource.Success) {
                loadBudgets()
            } else {
                 state = state.copy(error = result.message)
            }
        }
    }
}