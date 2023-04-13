package com.assignment.jumboshop.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.jumboshop.ui.StartScreen
import com.assignment.jumboshop.ui.productlist.ProductListScreen
import com.assignment.jumboshop.ui.productlist.ProductListViewModel

@Composable
fun JumboNavGraph() {
    val productsViewModel: ProductListViewModel = viewModel()

    val navController = rememberNavController()
    NavHost(navController, startDestination = Screens.Start.route) {
        composable(Screens.Start.route) {
            StartScreen(navController)
        }
        composable(Screens.ProductsList.route) {
            val cartItemsCount = 0
            val productListUiState = productsViewModel.productsState.collectAsState().value
            ProductListScreen(
                state = productListUiState,
                cartItemsCount = cartItemsCount,
                onAddToCart = { product ->

                },
                openCart = {
                    navController.navigate(Screens.Cart.route)
                },
                openDetails = {
                    navController.navigate(Screens.ProductDetails.route + "/$it")
                })
        }
    }
}
