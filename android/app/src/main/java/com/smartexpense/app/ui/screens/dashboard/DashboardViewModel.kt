package com.smartexpense.app.ui.screens.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartexpense.app.data.model.Transaction
import com.smartexpense.app.data.repository.AuthRepository
import com.smartexpense.app.data.repository.TransactionRepository
import com.smartexpense.app.data.repository.WalletRepository
import com.smartexpense.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    
    var dashboardState by mutableStateOf(DashboardState())
        private set
    
    init {
        loadDashboardData()
    }
    
    fun loadDashboardData() {
        viewModelScope.launch {
            dashboardState = dashboardState.copy(isLoading = true)
            
            // Load wallet balance
            when (val result = walletRepository.getWalletBalance()) {
                is Resource.Success -> {
                    dashboardState = dashboardState.copy(walletBalance = result.data)
                }
                is Resource.Error -> {
                    dashboardState = dashboardState.copy(
                        error = result.message
                    )
                }
                else -> {}
            }
            
            // Load transactions
            transactionRepository.syncTransactions()
            transactionRepository.getTransactions().collectLatest { transactions ->
                val expenses = transactions.filter { it.type == "expense" }
                val income = transactions.filter { it.type == "income" }
                
                dashboardState = dashboardState.copy(
                    transactions = transactions,
                    totalExpense = expenses.sumOf { it.amount },
                    totalIncome = income.sumOf { it.amount },
                    isLoading = false
                )
            }
        }
    }
    
    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLogoutComplete()
        }
    }
    
    fun deleteTransaction(id: String) {
        viewModelScope.launch {
            when (transactionRepository.deleteTransaction(id)) {
                is Resource.Success -> {
                    refreshData()
                }
                is Resource.Error -> {
                    dashboardState = dashboardState.copy(error = "Failed to delete transaction")
                }
                else -> {}
            }
        }
    }
    
    fun refreshData() {
        loadDashboardData()
    }
}

data class DashboardState(
    val isLoading: Boolean = false,
    val walletBalance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val transactions: List<Transaction> = emptyList(),
    val error: String? = null
)
