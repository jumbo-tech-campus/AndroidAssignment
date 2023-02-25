package com.abhinash.domain.repository

import com.abhinash.domain.models.CartProduct
import com.abhinash.domain.models.Product
import kotlinx.coroutines.flow.StateFlow

interface ProductRepository {
    suspend fun loadProducts(): List<Product>

    suspend fun saveProductToCart(product: Product, quantity: Int)

    fun getProductsForCart(): StateFlow<List<CartProduct>>

    suspend fun getCartProductsCount(): Int
}