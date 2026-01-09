package com.smartexpense.app.data.repository;

import com.smartexpense.app.data.model.AddTransactionRequest;
import com.smartexpense.app.data.model.Transaction;
import com.smartexpense.app.data.remote.ApiService;
import com.smartexpense.app.util.Resource;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00110\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/smartexpense/app/data/repository/TransactionRepository;", "", "apiService", "Lcom/smartexpense/app/data/remote/ApiService;", "(Lcom/smartexpense/app/data/remote/ApiService;)V", "addTransaction", "Lcom/smartexpense/app/util/Resource;", "Lcom/smartexpense/app/data/model/Transaction;", "request", "Lcom/smartexpense/app/data/model/AddTransactionRequest;", "(Lcom/smartexpense/app/data/model/AddTransactionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTransaction", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTransactions", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class TransactionRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.remote.ApiService apiService = null;
    
    @javax.inject.Inject()
    public TransactionRepository(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.remote.ApiService apiService) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTransactions(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<? extends java.util.List<com.smartexpense.app.data.model.Transaction>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addTransaction(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.model.AddTransactionRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<com.smartexpense.app.data.model.Transaction>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteTransaction(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
}