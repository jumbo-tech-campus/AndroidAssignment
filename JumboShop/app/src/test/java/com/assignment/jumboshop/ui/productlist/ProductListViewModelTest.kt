package com.assignment.jumboshop.ui.productlist

import arrow.core.Either
import com.assignment.domain.entities.CurrencyAmount
import com.assignment.domain.entities.ImageInfo
import com.assignment.domain.entities.ImageSize
import com.assignment.domain.entities.Prices
import com.assignment.domain.entities.Product
import com.assignment.domain.entities.UnitPrice
import com.assignment.domain.usecases.GetProductsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getProductsUseCase: GetProductsUseCase = mockk()
    private lateinit var viewModel: ProductListViewModel
    private val dummyProducts = listOf(
        Product(
            id = "1",
            title = "Product 1",
            prices = Prices(
                price = CurrencyAmount(currency = "USD", amount = 100.0),
                unitPrice = UnitPrice(
                    unit = "pcs",
                    price = CurrencyAmount(currency = "USD", amount = 100.0)
                )
            ),
            quantity = "5",
            imageInfo = ImageInfo(
                primaryView = listOf(
                    ImageSize(url = "https://dummy.url/image1.jpg", width = 100, height = 100)
                )
            )
        ),
        Product(
            id = "2",
            title = "Product 2",
            prices = Prices(
                price = CurrencyAmount(currency = "USD", amount = 200.0),
                unitPrice = UnitPrice(
                    unit = "pcs",
                    price = CurrencyAmount(currency = "USD", amount = 200.0)
                )
            ),
            quantity = "10",
            imageInfo = ImageInfo(
                primaryView = listOf(
                    ImageSize(url = "https://dummy.url/image2.jpg", width = 100, height = 100)
                )
            )
        )
    )
    @Before
    fun setup() {
        viewModel = ProductListViewModel(getProductsUseCase, testDispatcher)
    }

    @Test
    fun `loadProducts should emit Success on successful use case`() = runTest(testDispatcher) {
        coEvery { getProductsUseCase.execute() } returns flowOf(Either.Right(dummyProducts))
        launch {
            viewModel.loadProducts()
        }
        advanceUntilIdle()
        assertEquals(ProductListUiState.Success(dummyProducts), viewModel.productsState.value)
    }

    @Test
    fun `loadProducts should emit Error on failed use case`() = runTest(testDispatcher) {
        val exception = Exception("Error fetching products")
        coEvery { getProductsUseCase.execute() } returns flowOf(Either.Left(exception))
        launch {
            viewModel.loadProducts()
        }
        advanceUntilIdle()

        assertEquals(ProductListUiState.Error(exception), viewModel.productsState.value)
    }

    @Test
    fun `getProductById should return product for valid ID after success`() = runTest(testDispatcher) {
        coEvery { getProductsUseCase.execute() } returns flowOf(Either.Right(dummyProducts))

        var product:Product? = null
        launch {
            viewModel.loadProducts()
            product = viewModel.getProductById("1")
        }
        advanceUntilIdle()

        assertEquals(dummyProducts.first().id, product?.id)
    }

    @Test
    fun `getProductById should return null for invalid ID after success`() = runTest(testDispatcher) {
        coEvery { getProductsUseCase.execute() } returns flowOf(Either.Right(dummyProducts))

        viewModel.loadProducts()
        val product = viewModel.getProductById("3")
        assertNull(product)
    }
}


