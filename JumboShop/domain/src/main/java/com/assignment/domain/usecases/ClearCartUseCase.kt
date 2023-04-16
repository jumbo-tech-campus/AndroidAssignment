package com.assignment.domain.usecases

import com.assignment.domain.repository.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun execute() {
        cartRepository.clearCart()
    }
}
