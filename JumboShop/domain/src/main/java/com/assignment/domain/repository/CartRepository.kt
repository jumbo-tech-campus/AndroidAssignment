package com.assignment.domain.repository

import com.assignment.domain.entities.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(cartItem: CartItem)
    suspend fun incrementCartItem(itemId: String)
    suspend fun decrementCartItem(itemId: String)
    suspend fun deleteCartItem(itemId: String)
    suspend fun clearCart()
}
