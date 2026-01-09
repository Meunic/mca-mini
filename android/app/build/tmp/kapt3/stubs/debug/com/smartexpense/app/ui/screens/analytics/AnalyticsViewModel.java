package com.smartexpense.app.ui.screens.analytics;

import androidx.lifecycle.ViewModel;
import com.smartexpense.app.data.repository.TransactionRepository;
import com.smartexpense.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\u0010"}, d2 = {"Lcom/smartexpense/app/ui/screens/analytics/AnalyticsViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/smartexpense/app/data/repository/TransactionRepository;", "(Lcom/smartexpense/app/data/repository/TransactionRepository;)V", "<set-?>", "Lcom/smartexpense/app/ui/screens/analytics/AnalyticsState;", "state", "getState", "()Lcom/smartexpense/app/ui/screens/analytics/AnalyticsState;", "setState", "(Lcom/smartexpense/app/ui/screens/analytics/AnalyticsState;)V", "state$delegate", "Landroidx/compose/runtime/MutableState;", "loadAnalytics", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AnalyticsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.repository.TransactionRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState state$delegate = null;
    
    @javax.inject.Inject()
    public AnalyticsViewModel(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.repository.TransactionRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.ui.screens.analytics.AnalyticsState getState() {
        return null;
    }
    
    private final void setState(com.smartexpense.app.ui.screens.analytics.AnalyticsState p0) {
    }
    
    public final void loadAnalytics() {
    }
}