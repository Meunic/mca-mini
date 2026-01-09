package com.smartexpense.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val id: String = "",
    @SerializedName("user_id")
    val userId: String = "",
    val name: String,
    @SerializedName("synced")
    val synced: Boolean = false
)

data class AddCategoryRequest(
    val name: String
)
