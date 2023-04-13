package com.assignment.domain.usecases

import com.assignment.domain.entities.CartItem
import com.assignment.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun execute(): Flow<List<CartItem>> {
        return cartRepository.getCartItems()
    }
}
