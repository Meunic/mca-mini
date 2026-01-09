package com.smartexpense.app.data.repository

import com.smartexpense.app.data.local.TokenManager
import com.smartexpense.app.data.model.LoginRequest
import com.smartexpense.app.data.model.RegisterRequest
import com.smartexpense.app.data.remote.ApiService
import com.smartexpense.app.util.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    
    suspend fun register(name: String, email: String, password: String): Resource<Unit> {
        return try {
            val response = apiService.register(RegisterRequest(name, email, password))
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                tokenManager.saveToken(authResponse.token)
                tokenManager.saveUser(
                    authResponse.user.id,
                    authResponse.user.name,
                    authResponse.user.email
                )
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message() ?: "Registration failed")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
    
    suspend fun login(email: String, password: String): Resource<Unit> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                tokenManager.saveToken(authResponse.token)
                tokenManager.saveUser(
                    authResponse.user.id,
                    authResponse.user.name,
                    authResponse.user.email
                )
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message() ?: "Login failed")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
    
    suspend fun logout() {
        tokenManager.clearToken()
    }
    
    suspend fun isLoggedIn(): Boolean {
        return tokenManager.getToken().first() != null
    }
}
