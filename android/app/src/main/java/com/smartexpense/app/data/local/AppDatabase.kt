package com.smartexpense.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smartexpense.app.data.local.dao.BudgetDao
import com.smartexpense.app.data.local.dao.CategoryDao
import com.smartexpense.app.data.local.dao.TransactionDao
import com.smartexpense.app.data.model.Budget
import com.smartexpense.app.data.model.Category
import com.smartexpense.app.data.model.Transaction
import com.smartexpense.app.data.model.User

@Database(
    entities = [
        User::class,
        Transaction::class,
        Category::class,
        Budget::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao
    
    companion object {
        const val DATABASE_NAME = "smart_expense_db"
    }
}
