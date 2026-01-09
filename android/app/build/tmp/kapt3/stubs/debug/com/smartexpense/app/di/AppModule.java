package com.smartexpense.app.di;

import android.content.Context;
import androidx.room.Room;
import com.smartexpense.app.BuildConfig;
import com.smartexpense.app.data.local.AppDatabase;
import com.smartexpense.app.data.local.TokenManager;
import com.smartexpense.app.data.local.dao.BudgetDao;
import com.smartexpense.app.data.local.dao.CategoryDao;
import com.smartexpense.app.data.local.dao.TransactionDao;
import com.smartexpense.app.data.remote.ApiService;
import com.smartexpense.app.data.remote.AuthInterceptor;
import com.smartexpense.app.util.NetworkHelper;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\bH\u0007J\u0012\u0010\u0010\u001a\u00020\u00112\b\b\u0001\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0013H\u0007J\u0012\u0010\u0018\u001a\u00020\u00192\b\b\u0001\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\r\u001a\u00020\bH\u0007\u00a8\u0006\u001c"}, d2 = {"Lcom/smartexpense/app/di/AppModule;", "", "()V", "provideApiService", "Lcom/smartexpense/app/data/remote/ApiService;", "retrofit", "Lretrofit2/Retrofit;", "provideAppDatabase", "Lcom/smartexpense/app/data/local/AppDatabase;", "context", "Landroid/content/Context;", "provideBudgetDao", "Lcom/smartexpense/app/data/local/dao/BudgetDao;", "database", "provideCategoryDao", "Lcom/smartexpense/app/data/local/dao/CategoryDao;", "provideNetworkHelper", "Lcom/smartexpense/app/util/NetworkHelper;", "provideOkHttpClient", "Lokhttp3/OkHttpClient;", "authInterceptor", "Lcom/smartexpense/app/data/remote/AuthInterceptor;", "provideRetrofit", "okHttpClient", "provideTokenManager", "Lcom/smartexpense/app/data/local/TokenManager;", "provideTransactionDao", "Lcom/smartexpense/app/data/local/dao/TransactionDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.smartexpense.app.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient provideOkHttpClient(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.remote.AuthInterceptor authInterceptor) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit provideRetrofit(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient okHttpClient) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.data.remote.ApiService provideApiService(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.data.local.AppDatabase provideAppDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.data.local.dao.TransactionDao provideTransactionDao(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.local.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.data.local.dao.CategoryDao provideCategoryDao(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.local.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.data.local.dao.BudgetDao provideBudgetDao(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.local.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.data.local.TokenManager provideTokenManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.util.NetworkHelper provideNetworkHelper(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}