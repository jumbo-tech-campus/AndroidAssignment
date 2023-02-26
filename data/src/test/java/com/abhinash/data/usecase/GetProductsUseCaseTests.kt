package com.abhinash.data.usecase

import com.abhinash.domain.functional.Failure
import com.abhinash.domain.functional.getOrElse
import com.abhinash.domain.models.Product
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.GetProductsUseCase
import com.abhinash.testshared.builder.ProductBuilder
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetProductsUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setUp() {
        getProductsUseCase = GetProductsUseCaseImpl(productRepository)
    }

    @Test
    fun `execute returns products from repository`() = runBlocking {
        // Given
        val products = listOf(ProductBuilder().build())
        whenever(productRepository.loadProducts()).thenReturn(products)

        // When
        val result = getProductsUseCase.execute(Unit)

        // Then
        Truth.assertThat(result.isRight).isTrue()
        Truth.assertThat(result.getOrElse { emptyList<Product>() }).isEqualTo(products)
    }

    @Test
    fun `execute returns failure when repository throws exception`() {
        runBlocking {
            // Given
            val throwable = RuntimeException("Error loading products")
            whenever(productRepository.loadProducts()).thenThrow(throwable)

            // When
            val result = getProductsUseCase.execute(Unit)

            // Then
            Truth.assertThat(result.isLeft).isTrue()
            result.fold({
                Truth.assertThat(it).isInstanceOf(Failure.FeatureFailure::class.java)
                Truth.assertThat(it.exception)
                    .isEqualTo(throwable)
            }, {
                Truth.assertThat(it).isNull()
            })
        }
    }
}
