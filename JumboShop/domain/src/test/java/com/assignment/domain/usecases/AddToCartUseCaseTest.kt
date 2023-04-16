package com.assignment.domain.usecases

import com.assignment.domain.entities.CartItem
import com.assignment.domain.repository.CartRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AddToCartUseCaseTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var addToCartUseCase: AddToCartUseCase

    @Before
    fun setUp() {
        cartRepository = mock(CartRepository::class.java)
        addToCartUseCase = AddToCartUseCase(cartRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test add to cart`() = runTest {
        val cartItem = CartItem(id = "1",
            title = "Butter Bread",
            imageUrl = "https://images.com/item.png",
            price = 50.20,
            quantity = 2,
            currency = "EUR"
        )

        addToCartUseCase.execute(cartItem)

        verify(cartRepository).addToCart(cartItem)
    }
}
