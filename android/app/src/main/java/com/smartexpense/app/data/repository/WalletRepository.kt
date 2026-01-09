package com.smartexpense.app.data.repository

import com.smartexpense.app.data.model.WalletAdjustRequest
import com.smartexpense.app.data.remote.ApiService
import com.smartexpense.app.util.NetworkHelper
import com.smartexpense.app.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository @Inject constructor(
    private val apiService: ApiService,
    private val networkHelper: NetworkHelper
) {
    
    suspend fun getWalletBalance(): Resource<Double> {
        return try {
            if (!networkHelper.isNetworkAvailable()) {
                return Resource.Error("No internet connection")
            }
            
            val response = apiService.getWalletBalance()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.walletBalance)
            } else {
                Resource.Error(response.message() ?: "Failed to fetch wallet balance")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
    
    suspend fun adjustWallet(amount: Double, operation: String): Resource<Double> {
        return try {
            if (!networkHelper.isNetworkAvailable()) {
                return Resource.Error("No internet connection")
            }
            
            val response = apiService.adjustWallet(WalletAdjustRequest(amount, operation))
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.walletBalance)
            } else {
                Resource.Error(response.message() ?: "Failed to adjust wallet")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}
