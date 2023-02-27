package com.test.model

data class CartItem(
    val id: String,
    val name: String,
    val image: String,
    val price: Int,
    val currency: String,
    val quantity: Int
)
