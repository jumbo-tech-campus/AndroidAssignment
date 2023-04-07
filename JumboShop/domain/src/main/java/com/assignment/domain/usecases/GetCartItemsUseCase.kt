package com.assignment.domain.usecases

import com.assignment.domain.entities.CartItem
import com.assignment.domain.repository.CartRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun execute(): List<CartItem> {
        return cartRepository.getCartItems()
    }
}
