package com.smartexpense.app.data.remote;

import com.smartexpense.app.data.model.*;
import retrofit2.Response;
import retrofit2.http.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a6\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00032\b\b\u0001\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00190\u00032\b\b\u0001\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u001e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00190\u00032\b\b\u0001\u0010\u001a\u001a\u00020\u001bH\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u001a\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040 0\u0003H\u00a7@\u00a2\u0006\u0002\u0010!J\u001a\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0 0\u0003H\u00a7@\u00a2\u0006\u0002\u0010!J\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u00032\b\b\u0001\u0010\u0005\u001a\u00020%H\u00a7@\u00a2\u0006\u0002\u0010&J\u001e\u0010\'\u001a\b\u0012\u0004\u0012\u00020(0\u00032\b\b\u0001\u0010\u0005\u001a\u00020)H\u00a7@\u00a2\u0006\u0002\u0010*J\u001a\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0 0\u0003H\u00a7@\u00a2\u0006\u0002\u0010!J\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003H\u00a7@\u00a2\u0006\u0002\u0010!J\u001e\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u00032\b\b\u0001\u0010\u0005\u001a\u00020/H\u00a7@\u00a2\u0006\u0002\u00100J\u001e\u00101\u001a\b\u0012\u0004\u0012\u00020.0\u00032\b\b\u0001\u0010\u0005\u001a\u000202H\u00a7@\u00a2\u0006\u0002\u00103J\u001e\u00104\u001a\b\u0012\u0004\u0012\u0002050\u00032\b\b\u0001\u0010\u0005\u001a\u000206H\u00a7@\u00a2\u0006\u0002\u00107J\u001e\u00108\u001a\b\u0012\u0004\u0012\u0002090\u00032\b\b\u0001\u0010\u0005\u001a\u00020:H\u00a7@\u00a2\u0006\u0002\u0010;J(\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u001a\u001a\u00020\u001b2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010=\u00a8\u0006>"}, d2 = {"Lcom/smartexpense/app/data/remote/ApiService;", "", "addBudget", "Lretrofit2/Response;", "Lcom/smartexpense/app/data/model/Budget;", "request", "Lcom/smartexpense/app/data/model/AddBudgetRequest;", "(Lcom/smartexpense/app/data/model/AddBudgetRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addCategory", "Lcom/smartexpense/app/data/model/Category;", "Lcom/smartexpense/app/data/model/AddCategoryRequest;", "(Lcom/smartexpense/app/data/model/AddCategoryRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addTransaction", "Lcom/smartexpense/app/data/model/Transaction;", "Lcom/smartexpense/app/data/model/AddTransactionRequest;", "(Lcom/smartexpense/app/data/model/AddTransactionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "adjustWallet", "Lcom/smartexpense/app/data/model/WalletResponse;", "Lcom/smartexpense/app/data/model/WalletAdjustRequest;", "(Lcom/smartexpense/app/data/model/WalletAdjustRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "categorizeExpense", "Lcom/smartexpense/app/data/model/AICategorizeResponse;", "Lcom/smartexpense/app/data/model/AICategorizeRequest;", "(Lcom/smartexpense/app/data/model/AICategorizeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteBudget", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteCategory", "deleteTransaction", "getBudgets", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCategories", "getForecast", "Lcom/smartexpense/app/data/model/AIForecastResponse;", "Lcom/smartexpense/app/data/model/AIForecastRequest;", "(Lcom/smartexpense/app/data/model/AIForecastRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getInsights", "Lcom/smartexpense/app/data/model/AIInsightsResponse;", "Lcom/smartexpense/app/data/model/AIInsightsRequest;", "(Lcom/smartexpense/app/data/model/AIInsightsRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTransactions", "getWalletBalance", "login", "Lcom/smartexpense/app/data/model/AuthResponse;", "Lcom/smartexpense/app/data/model/LoginRequest;", "(Lcom/smartexpense/app/data/model/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "Lcom/smartexpense/app/data/model/RegisterRequest;", "(Lcom/smartexpense/app/data/model/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchTransactions", "Lcom/smartexpense/app/data/model/AISearchResponse;", "Lcom/smartexpense/app/data/model/AISearchRequest;", "(Lcom/smartexpense/app/data/model/AISearchRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "suggestBudget", "Lcom/smartexpense/app/data/model/AIBudgetSuggestionResponse;", "Lcom/smartexpense/app/data/model/AIBudgetSuggestionRequest;", "(Lcom/smartexpense/app/data/model/AIBudgetSuggestionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateBudget", "(Ljava/lang/String;Lcom/smartexpense/app/data/model/AddBudgetRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ApiService {
    
    @retrofit2.http.POST(value = "auth/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object register(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.RegisterRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.AuthResponse>> $completion);
    
    @retrofit2.http.POST(value = "auth/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.LoginRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.AuthResponse>> $completion);
    
    @retrofit2.http.GET(value = "wallet")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWalletBalance(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.WalletResponse>> $completion);
    
    @retrofit2.http.POST(value = "wallet/adjust")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object adjustWallet(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.WalletAdjustRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.WalletResponse>> $completion);
    
    @retrofit2.http.GET(value = "transactions")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTransactions(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.smartexpense.app.data.model.Transaction>>> $completion);
    
    @retrofit2.http.POST(value = "transactions")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addTransaction(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.AddTransactionRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.Transaction>> $completion);
    
    @retrofit2.http.DELETE(value = "transactions/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteTransaction(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.GET(value = "categories")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCategories(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.smartexpense.app.data.model.Category>>> $completion);
    
    @retrofit2.http.POST(value = "categories")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addCategory(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.AddCategoryRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.Category>> $completion);
    
    @retrofit2.http.DELETE(value = "categories/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteCategory(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.GET(value = "budgets")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getBudgets(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.smartexpense.app.data.model.Budget>>> $completion);
    
    @retrofit2.http.POST(value = "budgets")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object addBudget(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.AddBudgetRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.Budget>> $completion);
    
    @retrofit2.http.PUT(value = "budgets/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateBudget(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.AddBudgetRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.Budget>> $completion);
    
    @retrofit2.http.DELETE(value = "budgets/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteBudget(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.POST(value = "ai/categorize")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object categorizeExpense(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.AICategorizeRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.AICategorizeResponse>> $completion);
    
    @retrofit2.http.POST(value = "ai/suggest-budget")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object suggestBudget(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.AIBudgetSuggestionRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.AIBudgetSuggestionResponse>> $completion);
    
    @retrofit2.http.POST(value = "ai/search")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchTransactions(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.AISearchRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.AISearchResponse>> $completion);
    
    @retrofit2.http.POST(value = "ai/insights")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getInsights(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.AIInsightsRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.AIInsightsResponse>> $completion);
    
    @retrofit2.http.POST(value = "ai/forecast")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getForecast(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.AIForecastRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.smartexpense.app.data.model.AIForecastResponse>> $completion);
}