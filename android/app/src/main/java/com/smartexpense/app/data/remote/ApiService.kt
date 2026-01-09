package com.smartexpense.app.data.remote

import com.smartexpense.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // --- Auth ---
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    // --- Wallet ---
    @GET("wallet")
    suspend fun getWalletBalance(): Response<WalletResponse>
    
    @POST("wallet/adjust")
    suspend fun adjustWallet(@Body request: WalletAdjustRequest): Response<WalletResponse>

    // --- Transactions ---
    @GET("transactions")
    suspend fun getTransactions(): Response<List<Transaction>>

    @POST("transactions")
    suspend fun addTransaction(@Body request: AddTransactionRequest): Response<Transaction>

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: String): Response<Unit>

    // --- Categories (FIXED) ---
    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    // UPDATED: Now accepts AddCategoryRequest instead of Category
    @POST("categories")
    suspend fun addCategory(@Body request: AddCategoryRequest): Response<Category>

    @DELETE("categories/{id}")
    suspend fun deleteCategory(@Path("id") id: String): Response<Unit>

    // --- Budgets ---
    @GET("budgets")
    suspend fun getBudgets(): Response<List<Budget>>

    @POST("budgets")
    suspend fun addBudget(@Body request: AddBudgetRequest): Response<Budget>

    @PUT("budgets/{id}")
    suspend fun updateBudget(@Path("id") id: String, @Body request: AddBudgetRequest): Response<Budget>

    @DELETE("budgets/{id}")
    suspend fun deleteBudget(@Path("id") id: String): Response<Unit>

    // --- AI Features ---
    @POST("ai/categorize")
    suspend fun categorizeExpense(@Body request: AICategorizeRequest): Response<AICategorizeResponse>

    @POST("ai/suggest-budget")
    suspend fun suggestBudget(@Body request: AIBudgetSuggestionRequest): Response<AIBudgetSuggestionResponse>

    @POST("ai/search")
    suspend fun searchTransactions(@Body request: AISearchRequest): Response<AISearchResponse>

    @POST("ai/insights")
    suspend fun getInsights(@Body request: AIInsightsRequest): Response<AIInsightsResponse>

    @POST("ai/forecast")
    suspend fun getForecast(@Body request: AIForecastRequest): Response<AIForecastResponse>
}