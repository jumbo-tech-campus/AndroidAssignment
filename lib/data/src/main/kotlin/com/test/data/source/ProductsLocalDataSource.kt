package com.test.data.source

import com.test.network.local.ProductsDb
import com.test.network.local.entities.CartEntity
import javax.inject.Inject

class ProductsLocalDataSource @Inject constructor(
    private val database: ProductsDb
) {
    suspend fun saveProduct(cartEntity: CartEntity) =
        database.productDao().insertProduct(cartEntity)

    suspend fun getCart(): List<CartEntity> =
        database.productDao().getCart()

    suspend fun deleteItem(cart: CartEntity) =
        database.productDao().delete(cart)
}
