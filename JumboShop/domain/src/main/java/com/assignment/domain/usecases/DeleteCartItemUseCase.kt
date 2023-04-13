package com.assignment.domain.usecases

import com.assignment.domain.repository.CartRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun execute(itemId: String) {
        cartRepository.deleteCartItem(itemId)
    }
}
