package com.test.jumbo.products.states

data class CartInfoState(
    val uiState: UIState = UIState.IDLE,
    val showCart: Boolean = false
)
