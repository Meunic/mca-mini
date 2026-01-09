package com.smartexpense.app.ui.screens.expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartexpense.app.data.model.AddTransactionRequest
import com.smartexpense.app.data.model.Category
import com.smartexpense.app.data.repository.CategoryRepository
import com.smartexpense.app.data.repository.TransactionRepository
import com.smartexpense.app.data.repository.WalletRepository
import com.smartexpense.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val walletRepository: WalletRepository
) : ViewModel() {
    
    var state by mutableStateOf(AddExpenseState())
        private set
    
    init {
        loadCategories()
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.syncCategories()
            categoryRepository.getCategories().collectLatest { categories ->
                state = state.copy(availableCategories = categories)
            }
        }
    }
    
    fun updateAmount(amount: String) {
        state = state.copy(amount = amount, error = null)
    }
    
    fun updateCategory(category: String) {
        state = state.copy(category = category, error = null)
    }
    
    fun updateNote(note: String) {
        state = state.copy(note = note)
    }
    
    fun updateMethod(method: String) {
        state = state.copy(method = method)
    }
    
    fun updateType(type: String) {
        state = state.copy(type = type)
    }
    
    fun updateDate(date: Long) {
        state = state.copy(selectedDate = date)
    }
    
    fun addTransaction(onSuccess: () -> Unit) {
        val amount = state.amount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            state = state.copy(error = "Please enter a valid amount")
            return
        }
        
        if (state.category.isBlank()) {
            state = state.copy(error = "Please select a category")
            return
        }
        
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateString = dateFormat.format(Date(state.selectedDate))
            
            val request = AddTransactionRequest(
                amount = amount,
                date = dateString,
                category = state.category,
                method = state.method,
                note = state.note,
                type = state.type
            )
            
            when (val result = transactionRepository.addTransaction(request)) {
                is Resource.Success -> {
                    // Adjust wallet for expenses
                    if (state.type == "expense") {
                        walletRepository.adjustWallet(amount, "subtract")
                    } else {
                        walletRepository.adjustWallet(amount, "add")
                    }
                    state = state.copy(isLoading = false)
                    onSuccess()
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                else -> {
                    state = state.copy(isLoading = false)
                }
            }
        }
    }
    
    fun resetState() {
        state = AddExpenseState(availableCategories = state.availableCategories)
    }
}

data class AddExpenseState(
    val amount: String = "",
    val category: String = "",
    val note: String = "",
    val method: String = "Cash",
    val type: String = "expense",
    val selectedDate: Long = System.currentTimeMillis(),
    val availableCategories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
