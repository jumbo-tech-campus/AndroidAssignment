package com.test.data.usecase

import com.test.data.repository.ProductsLocalRepository
import com.test.model.Cart
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val productsLocalRepository: ProductsLocalRepository
) {

    suspend fun deleteCartItem(cart: Cart) =
        productsLocalRepository.deleteItemOnCart(cart)
}
