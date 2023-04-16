package com.assignment.domain.usecases

import arrow.core.Either
import com.assignment.domain.entities.Product
import com.assignment.domain.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetProductsUseCaseTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setUp() {
        productRepository = mock(ProductRepository::class.java)
        getProductsUseCase = GetProductsUseCase(productRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test get products success`() = runTest {
        val products = listOf<Product>(
            // add some sample Product instances here
        )
        `when`(productRepository.getProducts()).thenReturn(flowOf(Either.Right(products)))

        val result = getProductsUseCase.execute().toList().first()

        assertEquals(Either.Right(products), result)
    }
}
