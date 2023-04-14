package com.assignment.data.datasource.local.cart

import com.assignment.data.datasource.local.mappers.CartItemEntityMapper
import com.assignment.data.models.CartItemDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartLocalDataSource @Inject constructor(private val cartDao: CartDao) {
    suspend fun getCartItems(): Flow<List<CartItemDataModel>> {
        return cartDao.getCartItems().map {
            CartItemEntityMapper.entityListToModelDataList(it)
        }
    }

    suspend fun addToCart(cartItem: CartItemDataModel) {
        cartDao.addToCart(CartItemEntityMapper.modelToEntity(cartItem))
    }

    suspend fun incrementCartItem(itemId: String) {
        cartDao.incrementCartItem(itemId)
    }
    suspend fun decrementCartItem(itemId: String) {
        if (cartDao.getCartItem(itemId).quantity > 1) {
            cartDao.decrementCartItem(itemId)
        } else {
            cartDao.deleteCartItem(itemId)
        }
    }

    suspend fun deleteCartItem(itemId: String) {
        cartDao.deleteCartItem(itemId)
    }

   suspend fun clearCart() {
        cartDao.deleteAllCartItems()
    }
}
