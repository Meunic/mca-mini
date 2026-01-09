package com.smartexpense.app.data.model

import com.google.gson.annotations.SerializedName

data class WalletResponse(
    @SerializedName("wallet_balance")
    val walletBalance: Double
)

data class WalletAdjustRequest(
    val amount: Double,
    val operation: String // "add" or "subtract"
)
