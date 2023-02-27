package com.test.jumbo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.jumbo.products.ui.mainscreen.ProductsMainScreen
import com.test.jumbo.products.ScreenRoute
import com.test.jumbo.products.ui.startscreen.StartScreen
import com.test.jumbo.theme.JumboProductsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JumboProductsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.StartScreenRoute.route
                    ) {
                        composable(route = ScreenRoute.StartScreenRoute.route)
                        { StartScreen(navController) }

                        composable(route = ScreenRoute.MainProductsScreenRoute.route)
                        { ProductsMainScreen() }
                    }
                }
            }
        }
    }
}