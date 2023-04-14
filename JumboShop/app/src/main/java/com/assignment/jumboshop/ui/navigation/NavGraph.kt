package com.assignment.jumboshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.jumboshop.ui.StartScreen
import com.assignment.jumboshop.ui.cart.CartScreen
import com.assignment.jumboshop.ui.cart.CartViewModel
import com.assignment.jumboshop.ui.productdetails.ProductDetailsScreen
import com.assignment.jumboshop.ui.productlist.ProductListScreen
import com.assignment.jumboshop.ui.productlist.ProductListViewModel

@Composable
fun JumboNavGraph() {
    val productsViewModel: ProductListViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.Start.route) {
        composable(Screens.Start.route) {
            StartScreen(navController)
        }
        composable(Screens.ProductsList.route) {
            val cartItemsCount = cartViewModel.cartUiState.collectAsState().value.cartItems.size
            val productListUiState = productsViewModel.productsState.collectAsState().value
            ProductListScreen(
                state = productListUiState,
                cartItemsCount = cartItemsCount,
                onAddToCart = { product ->
                    cartViewModel.addToCart(product)
                },
                openCart = {
                    navController.navigate(Screens.Cart.route)
                },
                openDetails = {
                    navController.navigate(Screens.ProductDetails.route + "/$it")
                })
        }
        composable(route = Screens.ProductDetails.route + "/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val product = productsViewModel.getProductById(productId)
            val cartItemsCount = cartViewModel.cartUiState.collectAsState().value.cartItems.size
            if (product != null) {
                ProductDetailsScreen(product = product, cartItemsCount, onAddToCart = {
                    cartViewModel.addToCart(it)
                },
                    openCart = {
                        navController.navigate(Screens.Cart.route)
                    },navBack = {
                        navController.popBackStack()
                    })
            }
        }
        composable(Screens.Cart.route) {
            val  cartUiState = cartViewModel.cartUiState.collectAsState().value
            CartScreen(
                cartUiState = cartUiState,
                incrementItem = {
                    cartViewModel.incrementCartItem(it)
                },
                decrementItem = {
                    cartViewModel.decrementCartItem(it)
                },
                deleteItem = {
                    cartViewModel.deleteCartItem(it)
                },
                clearItems = {
                    cartViewModel.clearCart()
                },
                navBack = {
                    navController.popBackStack()
                })
        }
    }
}
