package com.test.jumbo.products.ui.cartscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jumbo.products.R
import com.test.jumbo.products.states.ProductsInfoState
import com.test.jumbo.products.ui.composables.CartList
import com.test.model.CartItem

@Composable
fun CartScreen(
    state: State<ProductsInfoState>,
    onDeleteItemClick: (CartItem) -> Unit,
    onIncreaseQuantityClick: (CartItem) -> Unit,
    onDecreaseQuantityClick: (CartItem) -> Unit
) {
    if (state.value.showCart) {
        Column(
            modifier = Modifier
                .background(colorResource(R.color.main_screen_background))
                .fillMaxSize()
        ) {
            CartList(
                cartItemList = state.value.cartItem,
                onDeleteItemClick = onDeleteItemClick,
                onIncreaseQuantityClick = onIncreaseQuantityClick,
                onDecreaseQuantityClick = onDecreaseQuantityClick,
            )
        }
    } else {
        EmptyScreen()
    }
}

@Composable
fun EmptyScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(colorResource(R.color.main_screen_background))
            .fillMaxSize()
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.empty_cart)
        )

        LottieAnimation(
            composition = composition,
            iterations = 2,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(300.dp)
                .fillMaxWidth()
                .fillMaxSize()
        )
    }
}
