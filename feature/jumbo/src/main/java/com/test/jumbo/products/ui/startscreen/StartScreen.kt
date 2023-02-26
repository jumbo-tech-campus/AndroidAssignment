package com.test.jumbo.products.ui.startscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jumbo.products.R
import com.test.jumbo.products.states.CartInfoState
import com.test.jumbo.products.ScreenRoute
import com.test.jumbo.products.viewmodel.CartViewModel

@Composable
fun StartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state = viewModel.viewStateFlow.collectAsState(initial = CartInfoState())
    if (state.value.showCart) {
        LaunchedEffect(Unit) {
            navController.navigate(ScreenRoute.MainProductsScreenRoute.route) {
                popUpTo(ScreenRoute.StartScreenRoute.route) { inclusive = true }
            }
        }
    } else {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.start_buying_animation)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(colorResource(R.color.main_screen_background))
                .fillMaxSize()
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                contentScale = ContentScale.FillBounds,
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.start_screen_proceed_to_list_message),
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = TextAlign.Center,
                color = colorResource(R.color.black)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier.padding(horizontal = 32.dp),
                onClick = {
                    navController.navigate(ScreenRoute.MainProductsScreenRoute.route) {
                        popUpTo(ScreenRoute.StartScreenRoute.route) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor =
                    colorResource(R.color.product_screen_main_color)
                )

            ) {
                Text(
                    text = stringResource(R.string.start_screen_button_start_buying_title),
                    color = colorResource(R.color.white)
                )
            }
        }
    }
}