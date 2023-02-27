package com.test.jumbo.products

import com.test.data.repository.ProductsLocalRepository
import com.test.data.usecase.GetCartUseCase
import com.test.jumbo.products.states.UIState
import com.test.jumbo.products.viewmodel.StartViewModel
import com.test.model.CartItem
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
class StartViewModelTest {

    private val productsLocalRepository: ProductsLocalRepository = mockk()
    private val getCartUseCase = GetCartUseCase(productsLocalRepository)
    private val startViewModel = StartViewModel(getCartUseCase)
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
    fun testGoToCartScreen() {
        runBlocking {
            whenever(productsLocalRepository.getCart()).thenReturn(listOf(mockedCartItem))
            whenever(getCartUseCase.getCart()).thenReturn(listOf(mockedCartItem))
            startViewModel.getCart()
            val job = launch {
                startViewModel.viewStateFlow.collect {
                    assertEquals(it.uiState, UIState.REQUEST_CONTENT_COMPLETED)
                    assertEquals(it.showCart, true)
                }
            }
            job.cancel()
        }
    }


    @Test
    fun testStayInStartHome() {
        runBlocking {
            whenever(productsLocalRepository.getCart()).thenReturn(listOf())
            whenever(getCartUseCase.getCart()).thenReturn(listOf())
            startViewModel.getCart()
            val job = launch {
                startViewModel.viewStateFlow.collect {
                    assertEquals(it.uiState, UIState.REQUEST_CONTENT_COMPLETED)
                    assertEquals(it.showCart, true)
                }
            }
            job.cancel()
        }
    }
}