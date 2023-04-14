package com.assignment.jumboshop.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.TransformOrigin
import androidx.lifecycle.viewmodel.compose.viewModel
import com.assignment.design_system.animations.bottomLeftRevealScaleIn
import com.assignment.design_system.animations.revealEnterTransition
import com.assignment.design_system.animations.topRightRevealScaleIn
import com.assignment.jumboshop.ui.StartScreen
import com.assignment.jumboshop.ui.cart.CartScreen
import com.assignment.jumboshop.ui.cart.CartViewModel
import com.assignment.jumboshop.ui.productdetails.ProductDetailsScreen
import com.assignment.jumboshop.ui.productlist.ProductListScreen
import com.assignment.jumboshop.ui.productlist.ProductListViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun JumboNavGraph() {
    val productsViewModel: ProductListViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = Screens.Start.route) {
        composable(Screens.Start.route) {
            StartScreen(navController)
        }
        composable(Screens.ProductsList.route,
            enterTransition = {
                revealEnterTransition()
            }) {
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
        composable(route = Screens.ProductDetails.route + "/{productId}",
            enterTransition = {
                scaleIn()
            }) { backStackEntry ->
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
        composable(Screens.Cart.route,
            enterTransition = {
                when(initialState.destination.route) {
                    Screens.ProductsList.route -> {
                        bottomLeftRevealScaleIn()
                    }
                    Screens.ProductDetails.route -> {
                        topRightRevealScaleIn()
                    }
                    else -> null
                }
            }
        ) {
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
