package com.assignment.jumboshop.ui.cart

import com.assignment.domain.entities.CartItem

data class CartUiState(
    val cartItems: List<CartItem>,
    val totalCost: Double
)
