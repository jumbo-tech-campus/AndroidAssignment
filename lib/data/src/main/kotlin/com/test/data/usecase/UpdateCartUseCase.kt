package com.test.data.usecase

import com.test.data.repository.ProductsLocalRepository
import com.test.model.CartItem
import javax.inject.Inject

class UpdateCartUseCase @Inject constructor(
    private val productsLocalRepository: ProductsLocalRepository
) {

    suspend fun updateCart(cartItem: CartItem) =
        productsLocalRepository.updateCart(cartItem)
}
