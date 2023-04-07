package com.assignment.domain.repository

import com.assignment.domain.entities.CartItem

interface CartRepository {
    suspend fun getCartItems(): List<CartItem>
    suspend fun addToCart(cartItem: CartItem)
    suspend fun updateCartItem(cartItem: CartItem)
    suspend fun deleteCartItem(cartItem: CartItem)
}
