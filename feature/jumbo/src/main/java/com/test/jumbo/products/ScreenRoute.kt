package com.test.jumbo.products

sealed class ScreenRoute(val route: String) {
    object StartScreenRoute: ScreenRoute("start_screen")
    object MainProductsScreenRoute: ScreenRoute("main_products_screen")
}
