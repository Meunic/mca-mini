package com.smartexpense.app.ui.screens.dashboard;

import androidx.lifecycle.ViewModel;
import com.smartexpense.app.data.model.Transaction;
import com.smartexpense.app.data.repository.AuthRepository;
import com.smartexpense.app.data.repository.TransactionRepository;
import com.smartexpense.app.data.repository.WalletRepository;
import com.smartexpense.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0013J\u0014\u0010\u0017\u001a\u00020\u00132\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0019J\u0006\u0010\u001a\u001a\u00020\u0013R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n8F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/smartexpense/app/ui/screens/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "walletRepository", "Lcom/smartexpense/app/data/repository/WalletRepository;", "transactionRepository", "Lcom/smartexpense/app/data/repository/TransactionRepository;", "authRepository", "Lcom/smartexpense/app/data/repository/AuthRepository;", "(Lcom/smartexpense/app/data/repository/WalletRepository;Lcom/smartexpense/app/data/repository/TransactionRepository;Lcom/smartexpense/app/data/repository/AuthRepository;)V", "<set-?>", "Lcom/smartexpense/app/ui/screens/dashboard/DashboardState;", "dashboardState", "getDashboardState", "()Lcom/smartexpense/app/ui/screens/dashboard/DashboardState;", "setDashboardState", "(Lcom/smartexpense/app/ui/screens/dashboard/DashboardState;)V", "dashboardState$delegate", "Landroidx/compose/runtime/MutableState;", "deleteTransaction", "", "id", "", "loadDashboardData", "logout", "onLogoutComplete", "Lkotlin/Function0;", "refreshData", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.repository.WalletRepository walletRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.repository.TransactionRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState dashboardState$delegate = null;
    
    @javax.inject.Inject()
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.repository.WalletRepository walletRepository, @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.repository.TransactionRepository transactionRepository, @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.ui.screens.dashboard.DashboardState getDashboardState() {
        return null;
    }
    
    private final void setDashboardState(com.smartexpense.app.ui.screens.dashboard.DashboardState p0) {
    }
    
    public final void loadDashboardData() {
    }
    
    public final void logout(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogoutComplete) {
    }
    
    public final void deleteTransaction(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void refreshData() {
    }
}