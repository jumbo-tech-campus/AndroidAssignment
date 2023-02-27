package com.test.jumbo.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.usecase.GetCartUseCase
import com.test.jumbo.products.states.CartInfoState
import com.test.jumbo.products.states.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase
) : ViewModel() {

    private val _viewStateFlow = MutableStateFlow(CartInfoState())
    val viewStateFlow: StateFlow<CartInfoState> = _viewStateFlow

    init {
        getCart()
    }

    fun getCart() {
        viewModelScope.launch {
            _viewStateFlow.value = viewStateFlow.value.copy(uiState = UIState.LOADING)
            val cartInfo = getCartUseCase.getCart()
            if (cartInfo.isNotEmpty()) {
                _viewStateFlow.value = CartInfoState(
                    uiState = UIState.REQUEST_CONTENT_COMPLETED,
                    showCart = true
                )
            } else {
                _viewStateFlow.value = CartInfoState(uiState = UIState.ERROR, showCart = false)
            }
        }
    }
}