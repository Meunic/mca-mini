package com.smartexpense.app.data.repository

import com.smartexpense.app.data.model.AddTransactionRequest
import com.smartexpense.app.data.model.Transaction
import com.smartexpense.app.data.remote.ApiService
import com.smartexpense.app.util.Resource
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getTransactions(): Resource<List<Transaction>> {
        return try {
            val response = apiService.getTransactions()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun addTransaction(request: AddTransactionRequest): Resource<Transaction> {
        return try {
            val response = apiService.addTransaction(request)
            if (response.isSuccessful && response.body() != null) {
                // FIXED: Return body() directly, not body()!!.transaction
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun deleteTransaction(id: String): Resource<Unit> {
        return try {
            val response = apiService.deleteTransaction(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}