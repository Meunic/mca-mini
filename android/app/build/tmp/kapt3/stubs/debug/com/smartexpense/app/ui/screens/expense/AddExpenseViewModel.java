package com.smartexpense.app.ui.screens.expense;

import androidx.lifecycle.ViewModel;
import com.smartexpense.app.data.model.AddTransactionRequest;
import com.smartexpense.app.data.model.Category;
import com.smartexpense.app.data.repository.CategoryRepository;
import com.smartexpense.app.data.repository.TransactionRepository;
import com.smartexpense.app.data.repository.WalletRepository;
import com.smartexpense.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\u0006\u0010\u0017\u001a\u00020\u0013J\u000e\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001aJ\u000e\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u00132\u0006\u0010!\u001a\u00020\u001aJ\u000e\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u001aJ\u000e\u0010$\u001a\u00020\u00132\u0006\u0010%\u001a\u00020\u001aR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n8F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/smartexpense/app/ui/screens/expense/AddExpenseViewModel;", "Landroidx/lifecycle/ViewModel;", "transactionRepository", "Lcom/smartexpense/app/data/repository/TransactionRepository;", "categoryRepository", "Lcom/smartexpense/app/data/repository/CategoryRepository;", "walletRepository", "Lcom/smartexpense/app/data/repository/WalletRepository;", "(Lcom/smartexpense/app/data/repository/TransactionRepository;Lcom/smartexpense/app/data/repository/CategoryRepository;Lcom/smartexpense/app/data/repository/WalletRepository;)V", "<set-?>", "Lcom/smartexpense/app/ui/screens/expense/AddExpenseState;", "state", "getState", "()Lcom/smartexpense/app/ui/screens/expense/AddExpenseState;", "setState", "(Lcom/smartexpense/app/ui/screens/expense/AddExpenseState;)V", "state$delegate", "Landroidx/compose/runtime/MutableState;", "addTransaction", "", "onSuccess", "Lkotlin/Function0;", "loadCategories", "resetState", "updateAmount", "amount", "", "updateCategory", "category", "updateDate", "date", "", "updateMethod", "method", "updateNote", "note", "updateType", "type", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AddExpenseViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.repository.TransactionRepository transactionRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.repository.CategoryRepository categoryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.repository.WalletRepository walletRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState state$delegate = null;
    
    @javax.inject.Inject()
    public AddExpenseViewModel(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.repository.TransactionRepository transactionRepository, @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.repository.CategoryRepository categoryRepository, @org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.repository.WalletRepository walletRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.ui.screens.expense.AddExpenseState getState() {
        return null;
    }
    
    private final void setState(com.smartexpense.app.ui.screens.expense.AddExpenseState p0) {
    }
    
    private final void loadCategories() {
    }
    
    public final void updateAmount(@org.jetbrains.annotations.NotNull()
    java.lang.String amount) {
    }
    
    public final void updateCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    public final void updateNote(@org.jetbrains.annotations.NotNull()
    java.lang.String note) {
    }
    
    public final void updateMethod(@org.jetbrains.annotations.NotNull()
    java.lang.String method) {
    }
    
    public final void updateType(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
    }
    
    public final void updateDate(long date) {
    }
    
    public final void addTransaction(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    public final void resetState() {
    }
}