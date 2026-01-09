package com.smartexpense.app.data.repository;

import com.smartexpense.app.data.model.WalletAdjustRequest;
import com.smartexpense.app.data.remote.ApiService;
import com.smartexpense.app.util.NetworkHelper;
import com.smartexpense.app.util.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086@\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/smartexpense/app/data/repository/WalletRepository;", "", "apiService", "Lcom/smartexpense/app/data/remote/ApiService;", "networkHelper", "Lcom/smartexpense/app/util/NetworkHelper;", "(Lcom/smartexpense/app/data/remote/ApiService;Lcom/smartexpense/app/util/NetworkHelper;)V", "adjustWallet", "Lcom/smartexpense/app/util/Resource;", "", "amount", "operation", "", "(DLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWalletBalance", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class WalletRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.remote.ApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.util.NetworkHelper networkHelper = null;
    
    @javax.inject.Inject()
    public WalletRepository(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.remote.ApiService apiService, @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.util.NetworkHelper networkHelper) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getWalletBalance(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<java.lang.Double>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object adjustWallet(double amount, @org.jetbrains.annotations.NotNull()
    java.lang.String operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<java.lang.Double>> $completion) {
        return null;
    }
}