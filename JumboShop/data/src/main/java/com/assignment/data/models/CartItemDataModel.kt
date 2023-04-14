package com.assignment.data.models

data class CartItemDataModel(
    val id: String,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val currency: String,
    val quantity: Int,
)
