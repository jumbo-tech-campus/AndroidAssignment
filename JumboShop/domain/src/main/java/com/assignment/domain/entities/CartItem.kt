package com.assignment.domain.entities

data class CartItem(
    val id: String,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val currency: String,
    val quantity: Int,
)
