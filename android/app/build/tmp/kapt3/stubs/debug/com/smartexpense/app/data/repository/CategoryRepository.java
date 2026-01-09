package com.smartexpense.app.data.repository;

import com.smartexpense.app.data.local.dao.CategoryDao;
import com.smartexpense.app.data.model.AddCategoryRequest;
import com.smartexpense.app.data.model.Category;
import com.smartexpense.app.data.remote.ApiService;
import com.smartexpense.app.util.NetworkHelper;
import com.smartexpense.app.util.Resource;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\n2\u0006\u0010\u0011\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00140\u0013J\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00100\nH\u0086@\u00a2\u0006\u0002\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/smartexpense/app/data/repository/CategoryRepository;", "", "apiService", "Lcom/smartexpense/app/data/remote/ApiService;", "categoryDao", "Lcom/smartexpense/app/data/local/dao/CategoryDao;", "networkHelper", "Lcom/smartexpense/app/util/NetworkHelper;", "(Lcom/smartexpense/app/data/remote/ApiService;Lcom/smartexpense/app/data/local/dao/CategoryDao;Lcom/smartexpense/app/util/NetworkHelper;)V", "addCategory", "Lcom/smartexpense/app/util/Resource;", "Lcom/smartexpense/app/data/model/Category;", "name", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteCategory", "", "id", "getCategories", "Lkotlinx/coroutines/flow/Flow;", "", "syncCategories", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class CategoryRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.remote.ApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.local.dao.CategoryDao categoryDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.util.NetworkHelper networkHelper = null;
    
    @javax.inject.Inject()
    public CategoryRepository(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.remote.ApiService apiService, @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.local.dao.CategoryDao categoryDao, @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.util.NetworkHelper networkHelper) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.smartexpense.app.data.model.Category>> getCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncCategories(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<com.smartexpense.app.data.model.Category>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.smartexpense.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
}