package com.test.jumbo.products.ui.mainscreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.jumbo.products.R
import com.test.jumbo.products.states.ProductsInfoState
import com.test.jumbo.products.ui.cartscreen.CartScreen
import com.test.jumbo.products.ui.productscreen.ProductScreen

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(
    @DrawableRes var icon: Int,
    @StringRes var title: Int,
    var screen: ComposableFun
) {
    data class Products(val state: State<ProductsInfoState>) : TabItem(
        R.drawable.ic_product_list,
        R.string.main_screen_products_tab_title,
        { ProductScreen(state) })

    data class Cart(var state: State<ProductsInfoState>) :
        TabItem(R.drawable.ic_shopping_cart,
            R.string.main_screen_cart_tab_title,
            { CartScreen(state) })
}