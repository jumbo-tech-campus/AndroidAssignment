package com.assignment.data.repository

import com.assignment.data.datasource.local.cart.CartLocalDataSource
import com.assignment.data.mappers.CartItemMapper
import com.assignment.domain.entities.CartItem
import com.assignment.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartLocalDataSource: CartLocalDataSource
) : CartRepository {

    override suspend fun getCartItems(): Flow<List<CartItem>> {
        return cartLocalDataSource.getCartItems().map {
            CartItemMapper.dataListToDomainModelList(it)
        }
    }

    override suspend fun addToCart(cartItem: CartItem) {
        cartLocalDataSource.addToCart(CartItemMapper.domainToDataModel(cartItem))
    }

    override suspend fun incrementCartItem(itemId: String) {
        cartLocalDataSource.incrementCartItem(itemId)
    }

    override suspend fun decrementCartItem(itemId: String) {
        cartLocalDataSource.decrementCartItem(itemId)
    }

    override suspend fun deleteCartItem(itemId: String) {
        cartLocalDataSource.deleteCartItem(itemId)
    }
}
