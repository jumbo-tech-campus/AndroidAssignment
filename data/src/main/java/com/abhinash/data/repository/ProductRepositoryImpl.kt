package com.abhinash.data.repository

import com.abhinash.data.contract.local.ProductLocalDataSource
import com.abhinash.domain.models.Product
import com.abhinash.domain.repository.ProductRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class ProductRepositoryImpl @Inject constructor(private val productLocalDataSource: ProductLocalDataSource) : ProductRepository {
    override suspend fun loadProducts() {
        productLocalDataSource.importProducts()
    }

    override fun saveProductToCart(productId: String, quantity: Int) {
        productLocalDataSource.saveProduct(productId, quantity)
    }

    override fun getProductsForCart(): StateFlow<List<Product>> =
        productLocalDataSource.loadProducts()

    override fun removeProductFromCart(productId: String) {
        productLocalDataSource.removeProduct(productId)
    }
}