package com.test.data.repository

import com.test.data.source.ProductsLocalDataSource
import com.test.model.Cart
import com.test.model.Product
import com.test.network.local.mapper.toCartEntity
import com.test.network.local.mapper.toCartListModel
import javax.inject.Inject

class ProductsLocalRepository @Inject constructor(
    private val productsLocalDataSource: ProductsLocalDataSource
) {

    suspend fun saveProduct(product: Product, quantity: Int) =
        productsLocalDataSource.saveProduct(product.toCartEntity(quantity))

    suspend fun getCart(): List<Cart> =
        productsLocalDataSource.getCart().toCartListModel()

    suspend fun deleteItemOnCart(cart: Cart) =
        productsLocalDataSource.deleteItem(cart.toCartEntity())

}
