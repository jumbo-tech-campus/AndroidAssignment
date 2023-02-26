package com.test.jumbo.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.usecase.DeleteCartItemUseCase
import com.test.data.usecase.GetCartUseCase
import com.test.data.usecase.GetProductListUseCase
import com.test.data.usecase.SaveProductUseCase
import com.test.jumbo.products.states.ProductsInfoState
import com.test.jumbo.products.states.UIState
import com.test.network.backend.model.mapper.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase
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
                                cart = cartInfo,
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
}