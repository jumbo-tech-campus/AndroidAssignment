package com.test.jumbo.products.ui.cartscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jumbo.products.R
import com.test.jumbo.products.states.ProductsInfoState

@Composable
fun CartScreen(state: State<ProductsInfoState>) {

    if(state.value.showCart){
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = TextAlign.Center,
            color = colorResource(R.color.black)
        )
    }
    else{

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
                modifier = Modifier.size(300.dp)
                    .fillMaxWidth()
                    .fillMaxSize()
            )
        }
    }

}