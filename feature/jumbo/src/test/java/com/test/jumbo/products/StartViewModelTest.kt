package com.test.jumbo.products

import com.test.data.repository.ProductsLocalRepository
import com.test.data.source.ProductsLocalDataSource
import com.test.data.usecase.GetCartUseCase
import com.test.jumbo.products.states.UIState
import com.test.jumbo.products.viewmodel.StartViewModel
import com.test.model.CartItem
import com.test.network.local.ProductsDb
import com.test.network.local.dao.ProductsDao
import com.test.network.local.entities.CartItemEntity
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
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class StartViewModelTest {

    private val productsDB: ProductsDb = mock()
    private val productsDao: ProductsDao = mock()

    private val productsLocalDataSource = ProductsLocalDataSource(productsDB)
    private val productsLocalRepository = ProductsLocalRepository(productsLocalDataSource)

    private val getCartUseCase = GetCartUseCase(productsLocalRepository)
    private lateinit var startViewModel: StartViewModel

    private val mockedCartItem = CartItem(
        id = "1", "Bread",
        "image/test.url",
        230, "EUR", 5
    )

    private val mockedCartItemEntity = CartItemEntity(
        uid = "1", "Bread",
        "image/test.url",
        230, "EUR", 5
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGoToCartScreen() {
        runBlocking {
            whenever(productsDao.getCart())
                .thenReturn(listOf(mockedCartItemEntity))
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().getCart())
                .thenReturn(listOf(mockedCartItemEntity))
            whenever(productsLocalDataSource.getCart()).thenReturn(listOf(mockedCartItemEntity))
            whenever(getCartUseCase.getCart()).thenReturn(listOf(mockedCartItem))

            initializeViewModel()
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
            whenever(productsDao.getCart())
                .thenReturn(listOf(mockedCartItemEntity))
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().getCart())
                .thenReturn(listOf(mockedCartItemEntity))
            whenever(productsLocalDataSource.getCart()).thenReturn(listOf(mockedCartItemEntity))
            whenever(getCartUseCase.getCart()).thenReturn(listOf())

            initializeViewModel()
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

    private fun initializeViewModel() {
        startViewModel = StartViewModel(getCartUseCase)
    }
}