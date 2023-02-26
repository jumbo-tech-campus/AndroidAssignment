package com.abhinash.jumboandroidassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhinash.domain.functional.Either
import com.abhinash.domain.functional.Failure
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.AddToCartUseCase
import com.abhinash.domain.usecase.GetProductsUseCase
import com.abhinash.jumboandroidassignment.ui.products.AddToCartState
import com.abhinash.jumboandroidassignment.ui.products.ListViewState
import com.abhinash.jumboandroidassignment.ui.products.ProductListFailure
import com.abhinash.jumboandroidassignment.ui.products.ProductListViewModel
import com.abhinash.testshared.CoroutineTestRule
import com.abhinash.testshared.builder.CartProductBuilder
import com.abhinash.testshared.builder.ProductBuilder
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProductListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var mockGetProductsUseCase: GetProductsUseCase

    @Mock
    private lateinit var mockProductRepository: ProductRepository

    @Mock
    private lateinit var mockAddToCartUseCase: AddToCartUseCase

    private lateinit var sut: ProductListViewModel

    @Before
    fun setUp() {
        sut = ProductListViewModel(
            mockGetProductsUseCase,
            mockProductRepository,
            mockAddToCartUseCase
        )
    }

    @Test
    fun `load products when repository returns non-empty list should set the success state`() =
        runBlocking {
            // Given
            val products = listOf(ProductBuilder().build())
            whenever(mockGetProductsUseCase.execute(Unit)).thenReturn(Either.Right(products))

            // When
            sut.loadProducts()

            // Then
            val productsState = sut.productList.value
            assertThat(productsState is ListViewState.Success).isTrue()
            assertThat((productsState as ListViewState.Success).products).isEqualTo(products)
        }

    @Test
    fun `load products when repository returns empty list should set the error state`() =
        runBlocking {
            // Given
            whenever(mockGetProductsUseCase.execute(Unit)).thenReturn(Either.Right(emptyList()))

            // When
            sut.loadProducts()

            // Then
            val productsState = sut.productList.value
            assertThat(productsState is ListViewState.Error).isTrue()
            assertThat((productsState as ListViewState.Error).failure is ProductListFailure.EmptyList).isTrue()
        }

    @Test
    fun `load products when repository returns failure should set the error state`() =
        runBlocking {
            // Given
            val exception = Exception()
            whenever(mockGetProductsUseCase.execute(Unit)).thenReturn(
                Either.Left(
                    Failure.FeatureFailure(
                        exception
                    )
                )
            )

            // When
            sut.loadProducts()

            // Then
            val productsState = sut.productList.value
            assertThat(productsState is ListViewState.Error).isTrue()
            assertThat((productsState as ListViewState.Error).failure is ProductListFailure.ExceptionWhileLoading).isTrue()
        }

    @Test
    fun `load cart count should set the correct count`() = runBlocking {
        // Given
        val cartProducts = listOf(CartProductBuilder().build())
        whenever(mockProductRepository.getProductsForCart()).thenReturn(
            MutableStateFlow(
                cartProducts
            )
        )

        // When
        sut.loadCartCount()

        // Then
        assertThat(sut.cartProductsCount.value).isEqualTo(cartProducts.size)
    }

    @Test
    fun `add to cart when use case returns success should set the success state`() =
        runBlocking {
            // Given
            val product = ProductBuilder().build()
            whenever(mockAddToCartUseCase.execute(product)).thenReturn(Either.Right(Unit))

            // When
            sut.addToCart(product)

            // Then
            assertThat(sut.addToCart.value is AddToCartState.Success).isTrue()
        }

    @Test
    fun `add to cart when use case returns failure should set the failure state`() =
        runBlocking {
            // Given
            val product = ProductBuilder().build()
            whenever(mockAddToCartUseCase.execute(product)).thenReturn(
                Either.Left(
                    Failure.FeatureFailure(
                        Exception()
                    )
                )
            )

            // When
            sut.addToCart(product)

            // Then
            assertThat(sut.addToCart.value is AddToCartState.Failed).isTrue()
        }
}
