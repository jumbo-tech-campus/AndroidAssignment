package com.assignment.jumboshop.ui.productlist.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.assignment.design_system.components.buttons.JumboButton
import com.assignment.design_system.components.texts.JumboBody
import com.assignment.design_system.components.texts.JumboSubtitle
import com.assignment.design_system.components.texts.JumboTitle
import com.assignment.domain.entities.Product

@Composable
fun ProductItem(product: Product, onAddToCart: (Product) -> Unit, onClick: (Product) -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable {
            onClick(product)
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProductImage(
                url = product.imageInfo.primaryView.firstOrNull()?.url ?: "",
                contentDescription = product.title,
                modifier = Modifier.size(80.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                JumboSubtitle(text = product.title + "\n", maxLines = 2, overflow = TextOverflow.Ellipsis)
                JumboBody(text = product.quantity)
                Spacer(modifier = Modifier.height(8.dp))
                JumboTitle(text = "${product.prices.price.currency} ${product.prices.price.amount}")

                JumboButton(text = "Add to Cart",onClick = { onAddToCart(product) }, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}
