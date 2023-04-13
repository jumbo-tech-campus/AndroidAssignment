package com.assignment.domain.usecases

import com.assignment.domain.repository.CartRepository
import javax.inject.Inject

class DecrementCartItemUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun execute(itemId: String) {
        cartRepository.decrementCartItem(itemId)
    }
}
