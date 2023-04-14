package com.assignment.domain.usecases

import com.assignment.domain.entities.CartItem
import com.assignment.domain.repository.CartRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetCartItemsUseCaseTest{
    private lateinit var cartRepository: CartRepository
    private lateinit var getCartItemsUseCase: GetCartItemsUseCase

    @Before
    fun setUp() {
        cartRepository = Mockito.mock(CartRepository::class.java)
        getCartItemsUseCase = GetCartItemsUseCase(cartRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test get cart items success`() = runTest {
        val items = listOf<CartItem>(
            // add some sample Product instances here
        )
        Mockito.`when`(cartRepository.getCartItems()).thenReturn(flowOf(items))

        val result = getCartItemsUseCase.execute().toList().first()

        assertEquals(items, result)
    }
}
