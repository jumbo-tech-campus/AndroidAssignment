package com.test.jumbo.products.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jumbo.products.R
import com.test.jumbo.products.ui.cartscreen.formatPrice
import com.test.model.CartItem

const val MIN_ITEM_TO_DELETE = 1

@Composable
fun SummaryItem(
    total: Int
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.cart_screen_total_title),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start)
                .alignBy(LastBaseline),
            style = TextStyle(
                color = colorResource(R.color.black)
            )
        )
        Text(
            text = formatPrice(total),
            modifier = Modifier.alignBy(LastBaseline),
            style = TextStyle(
                color = colorResource(R.color.black)
            )
        )
    }
}

@Composable
fun CartList(
    cartItemList: List<CartItem>,
    onDeleteItemClick: (CartItem) -> Unit,
    onIncreaseQuantityClick: (CartItem) -> Unit,
    onDecreaseQuantityClick: (CartItem) -> Unit
) {
    val resources = LocalContext.current.resources
    val quantityFormattedString = remember(cartItemList.size, resources) {
        resources.getQuantityString(
            R.plurals.cart_screen_order_quantity,
            cartItemList.size, cartItemList.size
        )
    }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.background(colorResource(id = R.color.main_screen_background))
    ) {
        item {
            Text(
                text = stringResource(R.string.cart_screen_order_title, quantityFormattedString),
                color = colorResource(id = R.color.black),
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
        items(
            items = cartItemList,
            itemContent = {
                CartItem(
                    cartItem = it,
                    onDeleteItemClick = onDeleteItemClick,
                    onIncreaseQuantityClick = onIncreaseQuantityClick,
                    onDecreaseQuantityClick = onDecreaseQuantityClick
                )
            }
        )
        item {
            Divider()
            SummaryItem(
                total = cartItemList.sumOf { it.price * it.quantity },
            )
        }
    }
}

@Composable
fun CartItem(
    cartItem: CartItem,
    onDeleteItemClick: (CartItem) -> Unit,
    onIncreaseQuantityClick: (CartItem) -> Unit,
    onDecreaseQuantityClick: (CartItem) -> Unit
) {

    Row(
        modifier = Modifier
            .background(colorResource(id = R.color.main_screen_background))
            .padding(vertical = 8.dp)
    ) {
        ProductImage(
            cartItem.image,
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
                text = cartItem.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                style = TextStyle(color = colorResource(R.color.black))
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = formatPrice(cartItem.price),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = colorResource(R.color.gray))
            )
            QuantitySelector(
                count = cartItem.quantity,
                decreaseItemCount = {
                    if (cartItem.quantity == MIN_ITEM_TO_DELETE) {
                        onDeleteItemClick(cartItem)
                    } else {
                        onDecreaseQuantityClick(cartItem)
                    }
                },
                increaseItemCount = { onIncreaseQuantityClick(cartItem) },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            )
        }
    }
}