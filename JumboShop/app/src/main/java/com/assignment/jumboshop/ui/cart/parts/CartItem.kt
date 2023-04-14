package com.assignment.jumboshop.ui.cart.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assignment.design_system.components.buttons.CircularButton
import com.assignment.design_system.components.texts.JumboSubtitle
import com.assignment.design_system.components.texts.JumboTitle
import com.assignment.domain.entities.CartItem
import com.assignment.jumboshop.ui.productlist.parts.ProductImage

@Composable
fun CartItem(
    cartItem: CartItem,
    increment: (CartItem) -> Unit,
    decrement: (CartItem) -> Unit,
    delete: (CartItem) -> Unit
) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProductImage(modifier = Modifier.size(width = 80.dp, height = 100.dp)
                .background(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp)),
                url = cartItem.imageUrl)

            Column(modifier = Modifier.padding(start = 16.dp)) {
                JumboSubtitle(text = cartItem.title)
                Spacer(modifier = Modifier.height(8.dp))
                JumboTitle(text = "${cartItem.currency} ${cartItem.price}")
                Row( modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CircularButton(text = "-", onClick = {
                            decrement(cartItem)
                        }, modifier = Modifier.size(28.dp))
                        JumboTitle(text = cartItem.quantity.toString())
                        CircularButton(text = "+", onClick = {
                            increment(cartItem)
                        }, modifier = Modifier.size(28.dp))
                    }
                    IconButton(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(100))
                        .size(34.dp), onClick = { delete(cartItem) }) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "delete item from cart",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
