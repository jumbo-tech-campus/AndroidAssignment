package com.test.jumbo.products.states

import com.test.model.CartItem
import com.test.model.Products

data class ProductsInfoState(
    val uiState: UIState = UIState.IDLE,
    val products: Products? = null,
    val cartItem: List<CartItem> = listOf(),
    val showCart: Boolean = false
)
