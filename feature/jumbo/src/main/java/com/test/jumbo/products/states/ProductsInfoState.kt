package com.test.jumbo.products.states

import com.test.model.Cart
import com.test.model.Products

data class ProductsInfoState(
    val uiState: UIState = UIState.IDLE,
    val products: Products? = null,
    val cart: List<Cart> = listOf(),
    val showCart: Boolean = false
)
