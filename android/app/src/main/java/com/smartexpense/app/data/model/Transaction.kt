package com.smartexpense.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey
    val id: String = "",
    @SerializedName("user_id")
    val userId: String = "",
    val amount: Double,
    val date: String,
    val category: String,
    val method: String,
    val note: String = "",
    @SerializedName("receipt_url")
    val receiptUrl: String? = null,
    val type: String, // "expense" or "income"
    @SerializedName("synced")
    val synced: Boolean = false
)

data class AddTransactionRequest(
    val amount: Double,
    val date: String,
    val category: String,
    val method: String,
    val note: String = "",
    @SerializedName("receipt_url")
    val receiptUrl: String? = null,
    val type: String
)

data class TransactionResponse(
    val transaction: Transaction
)
