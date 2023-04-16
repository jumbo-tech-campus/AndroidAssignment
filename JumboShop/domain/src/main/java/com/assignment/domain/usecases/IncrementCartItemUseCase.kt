package com.assignment.domain.usecases

import com.assignment.domain.repository.CartRepository
import javax.inject.Inject

class IncrementCartItemUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun execute(itemId: String) {
        cartRepository.incrementCartItem(itemId)
    }
}
