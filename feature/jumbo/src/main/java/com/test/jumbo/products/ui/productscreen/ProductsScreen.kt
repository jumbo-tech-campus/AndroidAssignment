package com.test.jumbo.products.ui.productscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.jumbo.products.R
import com.test.jumbo.products.states.ProductsInfoState
import com.test.model.Product
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest

@Composable
fun ProductScreen(state: State<ProductsInfoState>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.background(colorResource(id = R.color.main_screen_background))
    ) {
        items(
            items = state.value.products?.products ?: listOf(),
            itemContent = {
                ProductListItem(product = it)
            }
        )
    }
}

@Composable
fun ProductListItem(product: Product) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp),
        elevation = 2.dp,
        backgroundColor = colorResource(id = R.color.main_screen_background)
    ) {
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.product_screen_main_color))
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "${product.prices.price.amount}",
                style = TextStyle(
                    color = colorResource(R.color.white)
                ),
            )
            Text(
                text = "${product.prices.unitPrice.price.amount}/${product.prices.unitPrice.unit}",
                style = TextStyle(
                    color = colorResource(R.color.white)
                )
            )
        }
        Row {
            ProductImage(product)
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    style = TextStyle(color = colorResource(R.color.black))
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = product.quantity,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = colorResource(R.color.gray))
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 130.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                backgroundColor =
                colorResource(R.color.green)
            )
        ) {
            Text(
                text = stringResource(R.string.add_to_cart_button_text),
                color = colorResource(R.color.white)
            )
        }
    }
}

@Composable
private fun ProductImage(product: Product) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(product.imageInfo.primaryView.first().url)
            .setHeader("User-Agent", "Mozilla/5.0")
            .build(),
        loading = {
            CircularProgressIndicator()
        },
        contentDescription = stringResource(R.string.app_name),
        modifier = Modifier
            .size(140.dp)
            .padding(16.dp)
    )
}