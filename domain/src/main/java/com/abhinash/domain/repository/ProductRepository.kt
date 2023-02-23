package com.abhinash.domain.repository

import com.abhinash.domain.models.Product
import kotlinx.coroutines.flow.StateFlow

interface ProductRepository {
    suspend fun loadProducts()

    fun saveProductToCart(productId: String, quantity: Int)

    fun getProductsForCart(): StateFlow<List<Product>>

    fun removeProductFromCart(productId: String)
}