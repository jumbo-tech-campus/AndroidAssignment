package com.assignment.jumboshop.ui.cart

import com.assignment.domain.entities.CartItem
import com.assignment.domain.entities.CurrencyAmount
import com.assignment.domain.entities.ImageInfo
import com.assignment.domain.entities.ImageSize
import com.assignment.domain.entities.Prices
import com.assignment.domain.entities.Product
import com.assignment.domain.entities.UnitPrice
import com.assignment.domain.usecases.AddToCartUseCase
import com.assignment.domain.usecases.ClearCartUseCase
import com.assignment.domain.usecases.DecrementCartItemUseCase
import com.assignment.domain.usecases.DeleteCartItemUseCase
import com.assignment.domain.usecases.GetCartItemsUseCase
import com.assignment.domain.usecases.IncrementCartItemUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CartViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getCartItemsUseCase: GetCartItemsUseCase = mockk()
    private val addToCartUseCase: AddToCartUseCase = mockk()
    private val deleteCartItemUseCase: DeleteCartItemUseCase = mockk()
    private val clearCartUseCase: ClearCartUseCase = mockk()
    private val incrementCartItemUseCase: IncrementCartItemUseCase = mockk()
    private val decrementCartItemUseCase: DecrementCartItemUseCase = mockk()

    private lateinit var viewModel: CartViewModel

    private val dummyCartItems = listOf(
        CartItem(
            id = "1",
            title = "Dummy Item",
            imageUrl = "https://dummy.url/image.jpg",
            price = 10.0,
            currency = "EUR",
            quantity = 6
        ),
        CartItem(
            id = "2",
            title = "Dummy Item",
            imageUrl = "https://dummy.url/image.jpg",
            price = 10.0,
            currency = "EUR",
            quantity = 8
        )
    )
    private val dummyTotalCost = dummyCartItems.sumOf { it.price * it.quantity }

    private val dummyCartItem = CartItem(
        id = "2",
        title = "Dummy Item",
        imageUrl = "https://dummy.url/image.jpg",
        price = 10.0,
        currency = "EUR",
        quantity = 8
    )
    private val dummyProductInCart =  Product(
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
    private val dummyProductNotInCart =  Product(
        id = "90",
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
    @Before
    fun setup() {
        viewModel = CartViewModel(
            getCartItemsUseCase,
            addToCartUseCase,
            deleteCartItemUseCase,
            clearCartUseCase,
            incrementCartItemUseCase,
            decrementCartItemUseCase,
            testDispatcher
        )
    }

    @Test
    fun `fetchCartItems updates cartUiState with items and total cost`() = runTest(testDispatcher) {
        coEvery { getCartItemsUseCase.execute() } returns flowOf(dummyCartItems)
        launch {
            viewModel.fetchCartItems()
        }
        advanceUntilIdle()
        assertEquals(CartUiState(dummyCartItems, dummyTotalCost), viewModel.cartUiState.value)
    }

    @Test
    fun `addToCart adds new item to cart when not already present`() = runTest(testDispatcher) {
        coEvery { addToCartUseCase.execute(any()) } just Runs
        launch {
            viewModel.addToCart(dummyProductNotInCart)
        }
        advanceUntilIdle()
        coVerify { addToCartUseCase.execute(any()) }
    }

    @Test
    fun `addToCart increments existing item when already in cart`() = runTest(testDispatcher) {
        coEvery { getCartItemsUseCase.execute() } returns flowOf(dummyCartItems)
        coEvery { incrementCartItemUseCase.execute(any()) } just Runs
        launch {
            viewModel.fetchCartItems()
        }
        advanceUntilIdle()

        launch {
            viewModel.addToCart(dummyProductInCart)
        }
        advanceUntilIdle()
        coVerify { incrementCartItemUseCase.execute(any()) }
    }

    @Test
    fun `deleteCartItem removes item from cart`() = runTest(testDispatcher) {
        coEvery { deleteCartItemUseCase.execute(any()) } just Runs
        launch {
            viewModel.deleteCartItem(dummyCartItem)
        }
        advanceUntilIdle()
        coVerify { deleteCartItemUseCase.execute(dummyCartItem.id) }
    }

    @Test
    fun `clearCart clears all items from cart`() = runTest(testDispatcher) {
        coEvery { clearCartUseCase.execute() } just Runs
        launch {
            viewModel.clearCart()
        }
        advanceUntilIdle()
        coVerify { clearCartUseCase.execute() }
    }

    @Test
    fun `incrementCartItem increments item quantity in cart`() = runTest(testDispatcher) {
        coEvery { incrementCartItemUseCase.execute(any()) } just Runs
        launch {
            viewModel.incrementCartItem(dummyCartItem)
        }
        advanceUntilIdle()
        coVerify { incrementCartItemUseCase.execute(dummyCartItem.id) }
    }

    @Test
    fun `decrementCartItem decrements item quantity in cart`() = runTest(testDispatcher){
        coEvery { decrementCartItemUseCase.execute(any()) } just Runs
        launch {
            viewModel.decrementCartItem(dummyCartItem)
        }
        advanceUntilIdle()
        coVerify { decrementCartItemUseCase.execute(dummyCartItem.id) }
    }

}

