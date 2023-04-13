package com.assignment.jumboshop.ui.productlist.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShoppingCartFAB(cartItemsCount: Int, modifier: Modifier = Modifier,  action: () -> Unit,) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { action() }){
        Box {
            Icon(
                modifier = Modifier.size(44.dp),
                imageVector = Icons.Rounded.ShoppingCart,
                contentDescription = "cart"
            )
            if(cartItemsCount>0) {
                Text(
                    text = "$cartItemsCount",
                    modifier = Modifier
                        .background(
                            shape = CircleShape.copy(
                                CornerSize(100)
                            ),
                            color = Color.Red
                        )
                        .align(Alignment.TopEnd)
                        .size(20.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }
        }
    }
}
