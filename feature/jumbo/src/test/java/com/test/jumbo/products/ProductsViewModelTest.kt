package com.test.jumbo.products

import com.test.data.repository.ProductsLocalRepository
import com.test.data.repository.ProductsRepository
import com.test.data.usecase.UpdateCartUseCase
import com.test.data.usecase.DeleteCartItemUseCase
import com.test.data.usecase.GetCartUseCase
import com.test.data.usecase.GetProductListUseCase
import com.test.data.usecase.SaveProductUseCase
import com.test.jumbo.products.states.UIState
import com.test.jumbo.products.viewmodel.ProductsViewModel
import com.test.model.CartItem
import com.test.model.Products
import com.test.network.backend.model.mapper.NetworkResult
import com.test.network.backend.model.mapper.toProductModel
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    private val productsLocalRepository: ProductsLocalRepository = mockk()
    private val productsRepository: ProductsRepository = mockk()
    private val getProductListUseCase = GetProductListUseCase(productsRepository)
    private val getCartUseCase = GetCartUseCase(productsLocalRepository)
    private val saveProductUseCase = SaveProductUseCase(productsLocalRepository)
    private val deleteCartItemUseCase = DeleteCartItemUseCase(productsLocalRepository)
    private val updateCartUseCase = UpdateCartUseCase(productsLocalRepository)
    private val productsViewModel = ProductsViewModel(
        getProductListUseCase,
        saveProductUseCase,
        getCartUseCase,
        deleteCartItemUseCase,
        updateCartUseCase
    )
    private val mockedCartItem = CartItem(
        id = "1", "Bread",
        "image/test.url",
        230, "EUR", 5
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchProductListSuccessfully() {
        runBlocking {
            whenever(productsLocalRepository.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(getCartUseCase.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(getProductListUseCase.fetchProductListInfo())
                .thenReturn(
                    NetworkResult
                        .Success(Products(listOf(returnMockedProductResponse().toProductModel())))
                )
            productsViewModel.fetchProductList()
            val job = launch {
                productsViewModel.viewStateFlow.collect {
                    assertEquals(it.uiState, UIState.REQUEST_CONTENT_COMPLETED)
                    assertEquals(it.showCart, true)
                    assertEquals(it.products?.products?.size, 1)
                }
            }
            job.cancel()
        }
    }

    @Test
    fun fetchProductListWithApiError() {
        runBlocking {
            whenever(productsLocalRepository.getCart()).thenReturn(listOf())
            whenever(getCartUseCase.getCart()).thenReturn(listOf())
            whenever(getProductListUseCase.fetchProductListInfo())
                .thenReturn(
                    NetworkResult
                        .Fail(Products(listOf()))
                )
            productsViewModel.fetchProductList()
            val job = launch {
                productsViewModel.viewStateFlow.collect {
                    assertEquals(it.uiState, UIState.ERROR)
                    assertEquals(it.products?.products?.isEmpty(), true)
                    assertEquals(it.showCart, false)
                }
            }
            job.cancel()
        }
    }

    @Test
    fun fetchProductListWithApiFail() {
        runBlocking {
            whenever(productsLocalRepository.getCart()).thenReturn(listOf())
            whenever(getCartUseCase.getCart()).thenReturn(listOf())
            whenever(getProductListUseCase.fetchProductListInfo())
                .thenReturn(
                    NetworkResult
                        .Exception(Exception())
                )
            productsViewModel.fetchProductList()
            val job = launch {
                productsViewModel.viewStateFlow.collect {
                    assertEquals(it.uiState, UIState.ERROR)
                    assertEquals(it.products, null)
                    assertEquals(it.showCart, false)
                }
            }
            job.cancel()
        }
    }


    @Test
    fun testOnAddItemToCartClicked() {
        runBlocking {
            whenever(productsLocalRepository.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(getCartUseCase.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(saveProductUseCase.saveCartItem(mockedCartItem)).thenReturn(1)

            productsViewModel.onAddItemToCartClicked(
                3,
                returnMockedProductResponse().toProductModel()
            )
            val job = launch {
                productsViewModel.viewStateFlow.collect {
                    assertEquals(it.cartItem.size, 1)
                    assertEquals(it.showCart, true)
                }
            }
            job.cancel()
        }
    }

    @Test
    fun testOnIncreaseQuantityClicked() {
        runBlocking {
            whenever(productsLocalRepository.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(getCartUseCase.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(saveProductUseCase.saveCartItem(mockedCartItem)).thenReturn(1)

            productsViewModel.onIncreaseQuantityClicked(mockedCartItem)
            val job = launch {
                productsViewModel.viewStateFlow.collect {
                    assertEquals(it.cartItem.size, 1)
                    assertEquals(it.showCart, true)
                }
            }
            job.cancel()
        }
    }

    @Test
    fun testOnDecreaseQuantityClicked() {
        runBlocking {
            whenever(productsLocalRepository.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(getCartUseCase.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(updateCartUseCase.updateCart(mockedCartItem)).thenReturn(1)

            productsViewModel.onDecreaseQuantityClicked(mockedCartItem)
            val job = launch {
                productsViewModel.viewStateFlow.collect {
                    assertEquals(it.cartItem.size, 1)
                    assertEquals(it.showCart, true)
                }
            }
            job.cancel()
        }
    }

    @Test
    fun testOnDeleteProductClicked() {
        runBlocking {
            whenever(productsLocalRepository.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(getCartUseCase.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(deleteCartItemUseCase.deleteCartItem(mockedCartItem)).thenReturn(1)

            productsViewModel.onDeleteProductClicked(mockedCartItem)
            val job = launch {
                productsViewModel.viewStateFlow.collect {
                    assertEquals(it.cartItem.size, 0)
                    assertEquals(it.showCart, false)
                }
            }
            job.cancel()
        }
    }
}