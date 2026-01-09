package com.smartexpense.app.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.smartexpense.app.data.local.dao.BudgetDao;
import com.smartexpense.app.data.local.dao.CategoryDao;
import com.smartexpense.app.data.local.dao.TransactionDao;
import com.smartexpense.app.data.model.Budget;
import com.smartexpense.app.data.model.Category;
import com.smartexpense.app.data.model.Transaction;
import com.smartexpense.app.data.model.User;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\n"}, d2 = {"Lcom/smartexpense/app/data/local/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "budgetDao", "Lcom/smartexpense/app/data/local/dao/BudgetDao;", "categoryDao", "Lcom/smartexpense/app/data/local/dao/CategoryDao;", "transactionDao", "Lcom/smartexpense/app/data/local/dao/TransactionDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.smartexpense.app.data.model.User.class, com.smartexpense.app.data.model.Transaction.class, com.smartexpense.app.data.model.Category.class, com.smartexpense.app.data.model.Budget.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATABASE_NAME = "smart_expense_db";
    @org.jetbrains.annotations.NotNull()
    public static final com.smartexpense.app.data.local.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.smartexpense.app.data.local.dao.TransactionDao transactionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.smartexpense.app.data.local.dao.CategoryDao categoryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.smartexpense.app.data.local.dao.BudgetDao budgetDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/smartexpense/app/data/local/AppDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}