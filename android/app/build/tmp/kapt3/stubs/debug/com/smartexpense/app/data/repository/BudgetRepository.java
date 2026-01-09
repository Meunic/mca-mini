package com.smartexpense.app.data.repository;

import com.smartexpense.app.data.local.dao.BudgetDao;
import com.smartexpense.app.data.model.AddBudgetRequest;
import com.smartexpense.app.data.model.Budget;
import com.smartexpense.app.data.remote.ApiService;
import com.smartexpense.app.util.NetworkHelper;
import com.smartexpense.app.util.Resource;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ,\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\n2\u0006\u0010\u0014\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0012\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00180\u0017J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00130\nH\u0086@\u00a2\u0006\u0002\u0010\u001aJ4\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/smartexpense/app/data/repository/BudgetRepository;", "", "apiService", "Lcom/smartexpense/app/data/remote/ApiService;", "budgetDao", "Lcom/smartexpense/app/data/local/dao/BudgetDao;", "networkHelper", "Lcom/smartexpense/app/util/NetworkHelper;", "(Lcom/smartexpense/app/data/remote/ApiService;Lcom/smartexpense/app/data/local/dao/BudgetDao;Lcom/smartexpense/app/util/NetworkHelper;)V", "addBudget", "Lcom/smartexpense/app/util/Resource;", "Lcom/smartexpense/app/data/model/Budget;", "category", "", "amount", "", "period", "(Ljava/lang/String;DLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteBudget", "", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBudgets", "Lkotlinx/coroutines/flow/Flow;", "", "syncBudgets", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateBudget", "(Ljava/lang/String;Ljava/lang/String;DLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class BudgetRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.remote.ApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.local.dao.BudgetDao budgetDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.util.NetworkHelper networkHelper = null;
    
    @javax.inject.Inject()
    public BudgetRepository(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.remote.ApiService apiService, @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.local.dao.BudgetDao budgetDao, @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.util.NetworkHelper networkHelper) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.smartexpense.app.data.model.Budget>> getBudgets() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncBudgets(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addBudget(@org.jetbrains.annotations.NotNull()
    java.lang.String category, double amount, @org.jetbrains.annotations.NotNull()
    java.lang.String period, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<com.smartexpense.app.data.model.Budget>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateBudget(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String category, double amount, @org.jetbrains.annotations.NotNull()
    java.lang.String period, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<com.smartexpense.app.data.model.Budget>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteBudget(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
}