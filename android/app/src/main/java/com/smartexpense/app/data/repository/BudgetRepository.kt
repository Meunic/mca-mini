package com.smartexpense.app.data.repository

import com.smartexpense.app.data.local.dao.BudgetDao
import com.smartexpense.app.data.model.AddBudgetRequest
import com.smartexpense.app.data.model.Budget
import com.smartexpense.app.data.remote.ApiService
import com.smartexpense.app.util.NetworkHelper
import com.smartexpense.app.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepository @Inject constructor(
    private val apiService: ApiService,
    private val budgetDao: BudgetDao,
    private val networkHelper: NetworkHelper
) {
    
    fun getBudgets(): Flow<List<Budget>> {
        return budgetDao.getAllBudgets()
    }
    
    suspend fun syncBudgets(): Resource<Unit> {
        return try {
            if (!networkHelper.isNetworkAvailable()) {
                return Resource.Error("No internet connection")
            }
            
            val response = apiService.getBudgets()
            if (response.isSuccessful && response.body() != null) {
                val budgets = response.body()!!.map { it.copy(synced = true) }
                budgetDao.insertAllBudgets(budgets)
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message() ?: "Failed to fetch budgets")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
    
    suspend fun addBudget(category: String, amount: Double, period: String): Resource<Budget> {
        return try {
            if (networkHelper.isNetworkAvailable()) {
                val response = apiService.addBudget(AddBudgetRequest(category, amount, period))
                if (response.isSuccessful && response.body() != null) {
                    val budget = response.body()!!.copy(synced = true)
                    budgetDao.insertBudget(budget)
                    Resource.Success(budget)
                } else {
                    Resource.Error(response.message() ?: "Failed to add budget")
                }
            } else {
                val budget = Budget(
                    id = "temp_${System.currentTimeMillis()}",
                    category = category,
                    amount = amount,
                    period = period,
                    synced = false
                )
                budgetDao.insertBudget(budget)
                Resource.Success(budget)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
    
    suspend fun updateBudget(id: String, category: String, amount: Double, period: String): Resource<Budget> {
        return try {
            if (networkHelper.isNetworkAvailable()) {
                val response = apiService.updateBudget(id, AddBudgetRequest(category, amount, period))
                if (response.isSuccessful && response.body() != null) {
                    val budget = response.body()!!.copy(synced = true)
                    budgetDao.updateBudget(budget)
                    Resource.Success(budget)
                } else {
                    Resource.Error(response.message() ?: "Failed to update budget")
                }
            } else {
                Resource.Error("Cannot update offline")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
    
    suspend fun deleteBudget(id: String): Resource<Unit> {
        return try {
            if (networkHelper.isNetworkAvailable()) {
                val response = apiService.deleteBudget(id)
                if (response.isSuccessful) {
                    budgetDao.deleteBudgetById(id)
                    Resource.Success(Unit)
                } else {
                    Resource.Error(response.message() ?: "Failed to delete budget")
                }
            } else {
                budgetDao.deleteBudgetById(id)
                Resource.Success(Unit)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}
