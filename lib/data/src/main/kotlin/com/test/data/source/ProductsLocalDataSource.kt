package com.test.data.source

import com.test.network.local.ProductsDb
import com.test.network.local.entities.CartItemEntity
import javax.inject.Inject

class ProductsLocalDataSource @Inject constructor(
    private val database: ProductsDb
) {
    suspend fun saveCartItem(cartItemEntity: CartItemEntity) =
        database.productDao().insertOrUpdate(cartItemEntity)

    suspend fun updateProduct(cartItemEntity: CartItemEntity) =
        database.productDao().deleteOrUpdate(cartItemEntity)

    suspend fun getCart(): List<CartItemEntity> =
        database.productDao().getCart()
}
