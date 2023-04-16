package com.assignment.jumboshop.ui.productdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.assignment.design_system.components.buttons.JumboButton
import com.assignment.design_system.components.texts.JumboButtonText
import com.assignment.design_system.components.texts.JumboHeader
import com.assignment.design_system.components.texts.JumboSubtitle
import com.assignment.design_system.components.texts.JumboText
import com.assignment.domain.entities.Product
import com.assignment.jumboshop.ui.productlist.parts.ProductImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(product: Product,cartItemsCount:Int,
                         onAddToCart: (Product) -> Unit = {},
                         openCart: () -> Unit = {},
                         navBack: () -> Unit = {}) {
    Scaffold{
        Column(
            modifier = Modifier
                .padding(it)
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                ProductImage(
                    url = product.imageInfo.primaryView[1].url,
                    contentDescription = product.title,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .height(280.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        navBack()
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back to home"
                        )
                    }
                    TextButton(onClick = { openCart() }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.ShoppingCart,
                                contentDescription = "cart"
                            )
                            JumboButtonText(text = "Cart($cartItemsCount)")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStartPercent = 16, topEndPercent = 16)
            ) {
                Column(modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(34.dp)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    JumboHeader(text = product.title,)
                    Spacer(modifier = Modifier.height(16.dp))
                    JumboSubtitle(
                        text = "${product.prices.unitPrice.price.currency} ${product.prices.unitPrice.price.amount} / ${product.prices.unitPrice.unit}",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    JumboSubtitle(text = product.quantity)
                    Spacer(modifier = Modifier.height(24.dp))
                    Row {
                        JumboText(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(16.dp),
                            text = "${product.prices.price.currency} ${product.prices.price.amount}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        JumboButton(text = "Add to Cart") {
                            onAddToCart(product)
                        }
                    }
                }
            }
        }
    }
}
