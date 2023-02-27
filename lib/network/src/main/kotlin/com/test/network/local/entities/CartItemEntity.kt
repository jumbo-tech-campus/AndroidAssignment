package com.test.network.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItemEntity(
    @PrimaryKey(autoGenerate = false) val uid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "quantity") val quantity: Int
)