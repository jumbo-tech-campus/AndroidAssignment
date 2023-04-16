package com.assignment.domain.usecases

import com.assignment.domain.entities.CartItem
import com.assignment.domain.repository.CartRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class IncrementCartItemUseCaseTest{
    private lateinit var cartRepository: CartRepository
    private lateinit var incrementCartItemUseCase: IncrementCartItemUseCase

    @Before
    fun setUp() {
        cartRepository = Mockito.mock(CartRepository::class.java)
        incrementCartItemUseCase = IncrementCartItemUseCase(cartRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test increment cart item success`() = runTest {
        val cartItem = CartItem(
            id = "1",
            title = "Butter Bread",
            imageUrl = "https://images.com/item.png",
            price = 50.20,
            quantity = 2,
            currency = "EUR"
        )
        incrementCartItemUseCase.execute(cartItem.id)

        Mockito.verify(cartRepository).incrementCartItem(cartItem.id)
    }
}
