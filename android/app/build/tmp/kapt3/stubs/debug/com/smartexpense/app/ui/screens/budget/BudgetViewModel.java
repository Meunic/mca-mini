package com.smartexpense.app.ui.screens.budget;

import androidx.lifecycle.ViewModel;
import com.smartexpense.app.data.model.AddBudgetRequest;
import com.smartexpense.app.data.model.Budget;
import com.smartexpense.app.data.repository.BudgetRepository;
import com.smartexpense.app.data.repository.TransactionRepository;
import com.smartexpense.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0013J\u0006\u0010\u0018\u001a\u00020\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/smartexpense/app/ui/screens/budget/BudgetViewModel;", "Landroidx/lifecycle/ViewModel;", "budgetRepository", "Lcom/smartexpense/app/data/repository/BudgetRepository;", "transactionRepository", "Lcom/smartexpense/app/data/repository/TransactionRepository;", "(Lcom/smartexpense/app/data/repository/BudgetRepository;Lcom/smartexpense/app/data/repository/TransactionRepository;)V", "<set-?>", "Lcom/smartexpense/app/ui/screens/budget/BudgetState;", "state", "getState", "()Lcom/smartexpense/app/ui/screens/budget/BudgetState;", "setState", "(Lcom/smartexpense/app/ui/screens/budget/BudgetState;)V", "state$delegate", "Landroidx/compose/runtime/MutableState;", "addBudget", "", "category", "", "amount", "", "deleteBudget", "id", "loadBudgets", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class BudgetViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.repository.BudgetRepository budgetRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.repository.TransactionRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState state$delegate = null;
    
    @javax.inject.Inject()
    public BudgetViewModel(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.repository.BudgetRepository budgetRepository, @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.repository.TransactionRepository transactionRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.ui.screens.budget.BudgetState getState() {
        return null;
    }
    
    private final void setState(com.smartexpense.app.ui.screens.budget.BudgetState p0) {
    }
    
    public final void loadBudgets() {
    }
    
    public final void addBudget(@org.jetbrains.annotations.NotNull()
    java.lang.String category, double amount) {
    }
    
    public final void deleteBudget(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
}