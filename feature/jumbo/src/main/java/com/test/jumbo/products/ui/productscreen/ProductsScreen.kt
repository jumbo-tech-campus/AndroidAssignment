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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jumbo.products.R
import com.test.jumbo.products.states.ProductsInfoState
import com.test.model.Product
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.test.jumbo.products.ui.cartscreen.formatPrice
import com.test.jumbo.products.ui.composables.ProductImage

@Composable
fun ProductScreen(
    state: State<ProductsInfoState>,
    onAddItemClick: (Int, Product) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.background(colorResource(id = R.color.main_screen_background))
    ) {
        items(
            items = state.value.products?.products ?: listOf(),
            itemContent = {
                ProductListItem(
                    product = it,
                    onAddItemClick = onAddItemClick,
                )
            }
        )
    }
}

@Composable
fun ProductListItem(
    product: Product,
    onAddItemClick: (Int, Product) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp),
        elevation = 2.dp,
        backgroundColor = colorResource(id = R.color.main_screen_background)
    ) {
        Row {
            ProductImage(
                product.imageInfo.primaryView.first().url,
                modifier = Modifier
                    .size(140.dp)
                    .padding(16.dp)
            )
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

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "${formatPrice(product.prices.price.amount)}${
                        stringResource(id = R.string.cart_screen_price_per_unity_title)
                    }",
                    style = TextStyle(
                        color = colorResource(R.color.green)
                    ),
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "${
                        formatPrice(product.prices.unitPrice.price.amount)
                    }/${product.prices.unitPrice.unit}",
                    style = TextStyle(
                        color = colorResource(R.color.green)
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 150.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
            onClick = { onAddItemClick(1, product) },
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