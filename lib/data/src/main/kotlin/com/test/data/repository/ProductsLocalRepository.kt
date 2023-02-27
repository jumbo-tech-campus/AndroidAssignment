package com.test.data.repository

import com.test.data.source.ProductsLocalDataSource
import com.test.model.CartItem
import com.test.model.Product
import com.test.network.local.mapper.toCartItemEntity
import com.test.network.local.mapper.toCartItemListModel
import javax.inject.Inject

class ProductsLocalRepository @Inject constructor(
    private val productsLocalDataSource: ProductsLocalDataSource
) {

    suspend fun saveCartItem(cartItem: CartItem) =
        productsLocalDataSource.saveCartItem(cartItem.toCartItemEntity())

    suspend fun saveProduct(product: Product, quantity: Int) =
        productsLocalDataSource.saveCartItem(product.toCartItemEntity(quantity))

    suspend fun updateCart(cartItem: CartItem) =
        productsLocalDataSource.updateProduct(cartItem.toCartItemEntity())

    suspend fun getCart(): List<CartItem> =
        productsLocalDataSource.getCart().toCartItemListModel()

}
