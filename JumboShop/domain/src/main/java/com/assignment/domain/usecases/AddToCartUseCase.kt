package com.assignment.domain.usecases

import com.assignment.domain.entities.CartItem
import com.assignment.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun execute(cartItem: CartItem) {
        cartRepository.addToCart(cartItem)
    }
}
