package com.assignment.jumboshop.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.domain.entities.CartItem
import com.assignment.domain.entities.Product
import com.assignment.domain.usecases.AddToCartUseCase
import com.assignment.domain.usecases.ClearCartUseCase
import com.assignment.domain.usecases.DecrementCartItemUseCase
import com.assignment.domain.usecases.DeleteCartItemUseCase
import com.assignment.domain.usecases.GetCartItemsUseCase
import com.assignment.domain.usecases.IncrementCartItemUseCase
import com.assignment.jumboshop.di.IO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val incrementCartItemUseCase: IncrementCartItemUseCase,
    private val decrementCartItemUseCase: DecrementCartItemUseCase,
    @IO private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _cartUiState = MutableStateFlow(CartUiState(emptyList(), 0.0))
    val cartUiState: StateFlow<CartUiState> = _cartUiState

    init {
        fetchCartItems()
    }

    fun fetchCartItems() {
        viewModelScope.launch(dispatcher) {
            getCartItemsUseCase.execute().collectLatest { items ->
                _cartUiState.value = CartUiState(
                    cartItems = items,
                    totalCost = items.sumOf { it.price * it.quantity }
                )
            }
        }
    }

    fun addToCart(product: Product) {
        val cartItem = CartItem(id = product.id,
            title = product.title,
            imageUrl = product.imageInfo.primaryView.firstOrNull()?.url ?: "",
            price = product.prices.price.amount,
            currency = product.prices.price.currency,
            quantity = 1)
        if (_cartUiState.value.cartItems.find { it.id == product.id } == null) {
            viewModelScope.launch(dispatcher) {
                addToCartUseCase.execute(cartItem)
            }
        } else {
            incrementCartItem(cartItem)
        }
    }

    fun deleteCartItem(item: CartItem) {
        viewModelScope.launch(dispatcher) {
            deleteCartItemUseCase.execute(item.id)
        }
    }
    fun clearCart() {
        viewModelScope.launch(dispatcher) {
            clearCartUseCase.execute()
        }
    }
    fun incrementCartItem(item: CartItem) {
        viewModelScope.launch(dispatcher) {
            incrementCartItemUseCase.execute(item.id)
        }
    }
    fun decrementCartItem(item: CartItem) {
        viewModelScope.launch(dispatcher) {
            decrementCartItemUseCase.execute(item.id)
        }
    }
}
