package com.abhinash.data.contract.local

import com.abhinash.domain.models.Product
import kotlinx.coroutines.flow.StateFlow

interface ProductLocalDataSource {
    suspend fun importProducts()

    fun saveProduct(productId: String, quantity: Int)

    fun loadProducts(): StateFlow<List<Product>>

    fun removeProduct(productId: String)
}