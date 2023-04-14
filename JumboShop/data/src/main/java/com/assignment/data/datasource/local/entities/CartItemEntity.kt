package com.assignment.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItemEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val currency: String,
    val quantity: Int,
)
