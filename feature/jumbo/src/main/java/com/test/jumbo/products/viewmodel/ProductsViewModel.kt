package com.test.jumbo.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.usecase.GetCartUseCase
import com.test.data.usecase.GetProductListUseCase
import com.test.data.usecase.SaveProductUseCase
import com.test.data.usecase.UpdateCartUseCase
import com.test.data.usecase.DeleteCartItemUseCase
import com.test.jumbo.products.states.ProductsInfoState
import com.test.jumbo.products.states.UIState
import com.test.model.CartItem
import com.test.model.Product
import com.test.network.backend.model.mapper.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DB_QUERY_RESULT = 0

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val updateCartUseCase: UpdateCartUseCase
) : ViewModel() {

    private val _viewStateFlow = MutableStateFlow(ProductsInfoState())
    val viewStateFlow: StateFlow<ProductsInfoState> = _viewStateFlow

    init {
        fetchProductList()
    }

    fun fetchProductList() {
        viewModelScope.launch {
            _viewStateFlow.value = viewStateFlow.value.copy(uiState = UIState.LOADING)
            flowOf(
                getCartUseCase.getCart()
            ).zip(
                flowOf(
                    getProductListUseCase.fetchProductListInfo()
                )
            ) { cartInfo, productsFromNetwork ->
                when (productsFromNetwork) {
                    is NetworkResult.Success -> {
                        _viewStateFlow.value = viewStateFlow
                            .value.copy(
                                cartItem = cartInfo,
                                uiState = if (productsFromNetwork.result != null)
                                    UIState.REQUEST_CONTENT_COMPLETED
                                else UIState.ERROR,
                                products = productsFromNetwork.result,
                                showCart = cartInfo.isNotEmpty()
                            )
                    }
                    else -> {
                        _viewStateFlow.value = ProductsInfoState(uiState = UIState.ERROR)
                    }
                }
            }.collect()
        }
    }

    fun onAddItemToCartClicked(quantity: Int, product: Product) {
        viewModelScope.launch {
            val result = saveProductUseCase.saveProduct(product, quantity)
            if (result > DB_QUERY_RESULT) {
                val cartInfo = getCartUseCase.getCart()
                _viewStateFlow.value = viewStateFlow
                    .value.copy(
                        cartItem = cartInfo,
                        showCart = cartInfo.isNotEmpty()
                    )
            }
        }
    }

    fun onIncreaseQuantityClicked(cartItem: CartItem) {
        viewModelScope.launch {
            val result = saveProductUseCase.saveCartItem(cartItem)
            if (result > DB_QUERY_RESULT) {
                val cartInfo = getCartUseCase.getCart()
                _viewStateFlow.value = viewStateFlow
                    .value.copy(
                        cartItem = cartInfo,
                        showCart = cartInfo.isNotEmpty()
                    )
            }
        }
    }

    fun onDecreaseQuantityClicked(cartItem: CartItem) {
        viewModelScope.launch {
            val result = updateCartUseCase.updateCart(cartItem)
            if (result > DB_QUERY_RESULT) {
                val cartInfo = getCartUseCase.getCart()
                _viewStateFlow.value = viewStateFlow
                    .value.copy(
                        cartItem = cartInfo,
                        showCart = cartInfo.isNotEmpty()
                    )
            }
        }
    }

    fun onDeleteProductClicked(cartItem: CartItem) {
        viewModelScope.launch {
            val result = deleteCartItemUseCase.deleteCartItem(cartItem)
            if (result > DB_QUERY_RESULT) {
                val cartInfo = getCartUseCase.getCart()
                _viewStateFlow.value = viewStateFlow
                    .value.copy(
                        cartItem = cartInfo,
                        showCart = cartInfo.isNotEmpty()
                    )
            }
        }
    }
}