package com.smartexpense.app.data.remote

import com.smartexpense.app.data.local.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        // Skip auth for login/register endpoints
        if (request.url.encodedPath.contains("auth/login") || 
            request.url.encodedPath.contains("auth/register")) {
            return chain.proceed(request)
        }
        
        // Add auth token
        val token = runBlocking {
            tokenManager.getToken().first()
        }
        
        val newRequest = if (token != null) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }
        
        return chain.proceed(newRequest)
    }
}
