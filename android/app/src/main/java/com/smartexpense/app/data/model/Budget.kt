package com.smartexpense.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey
    val id: String = "",
    @SerializedName("user_id")
    val userId: String = "",
    val category: String,
    val amount: Double,
    val period: String, // "monthly" or "weekly"
    @SerializedName("synced")
    val synced: Boolean = false
)

data class AddBudgetRequest(
    val category: String,
    val amount: Double,
    val period: String
)
