package com.abhinash.data.usecase

import com.abhinash.domain.functional.Either
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.AddToCartUseCase
import com.abhinash.testshared.builder.CartProductBuilder
import com.abhinash.testshared.builder.ProductBuilder
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddToCartUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var addToCartUseCaseImpl: AddToCartUseCase

    @Before
    fun setUp() {
        addToCartUseCaseImpl = AddToCartUseCaseImpl(productRepository)
    }

    @Test
    fun `execute should save product to cart and return right`() = runBlocking {
        // given
        val product = ProductBuilder().build()
        val cartProducts = listOf(CartProductBuilder().build())
        whenever(productRepository.getProductsForCart()).thenReturn(MutableStateFlow(cartProducts))

        // when
        val result = addToCartUseCaseImpl.execute(product)

        // then
        Truth.assertThat(result).isEqualTo(Either.Right(Unit))
        verify(productRepository).saveProductToCart(eq(product), eq(1))
    }

    @Test
    fun `execute should return left on failure`() = runBlocking {
        // given
        val product = ProductBuilder().build()
        whenever(productRepository.getProductsForCart()).thenAnswer { throw Exception() }

        // when
        val result = addToCartUseCaseImpl.execute(product)

        // then
        Truth.assertThat(result.isLeft).isTrue()
    }
}
