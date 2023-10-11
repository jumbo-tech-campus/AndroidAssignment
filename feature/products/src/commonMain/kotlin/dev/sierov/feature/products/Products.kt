@file:OptIn(ExperimentalMaterialApi::class)

package dev.sierov.feature.products

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import dev.sierov.domain.model.product.Product
import dev.sierov.screen.ProductsScreen
import me.tatarka.inject.annotations.Inject

@Inject
class ProductsUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is ProductsScreen -> ui<ProductsUiState> { state, modifier -> Products(state, modifier) }
        else -> null
    }
}

@Composable
fun Products(
    state: ProductsUiState,
    modifier: Modifier = Modifier
) {
    val eventSink = state.eventSink
    Products(
        state = state,
        onRefreshProducts = { eventSink(ProductsUiEvent.RefreshProducts) },
        onAddProduct = { eventSink(ProductsUiEvent.AddProduct(it)) },
        onRemoveProduct = { eventSink(ProductsUiEvent.RemoveProduct(it)) },
        modifier = modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Products(
    state: ProductsUiState,
    onRefreshProducts: () -> Unit,
    onAddProduct: (Product) -> Unit,
    onRemoveProduct: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.refreshing,
        onRefresh = onRefreshProducts,
    )
    Box(modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(Modifier.matchParentSize()) {
            items(state.products) { product ->
                ProductItem(
                    product = product,
                    quantity = state.shoppingContent[product.id],
                    onAddProduct = onAddProduct,
                    onRemoveProduct = onRemoveProduct,
                    onClick = { /* no-op */ },
                    modifier = Modifier
                )
            }
        }
        PullRefreshIndicator(
            refreshing = state.refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ProductItem(
    product: Product,
    quantity: Int,
    onAddProduct: (Product) -> Unit,
    onRemoveProduct: (Product) -> Unit,
    onClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier
        .padding(4.dp)
        .clickable { onClick(product) }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val painter = if (product.imageInfo.primary.isEmpty()) {
                    rememberVectorPainter(Icons.Filled.ImageNotSupported)
                } else {
                    rememberImagePainter(url = product.imageInfo.smallestOrNull()!!.url)
                }
                Image(
                    painter = painter,
                    contentDescription = product.title,
                    modifier = Modifier.size(80.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = product.quantity,
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = "${product.prices.price}",
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            QuantityButton(
                quantity = quantity,
                onPlus = { onAddProduct(product) },
                onMinus = { onRemoveProduct(product) },
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
private fun QuantityButton(
    quantity: Int,
    onPlus: () -> Unit,
    onMinus: () -> Unit,
    expandedContainerColor: Color = Color.White,
    collapsedContainerColor: Color = Color(0xFF0FC646),
    modifier: Modifier
) {
    val expanded = quantity > 0
    val containerColor by animateColorAsState(
        targetValue = if (expanded) expandedContainerColor else collapsedContainerColor
    )
    val contentColor by animateColorAsState(
        targetValue = if (expanded) Color.Black else Color.White
    )
    val buttonElevation by animateDpAsState(
        targetValue = if (expanded) 2.dp else 0.dp
    )

    val content = @Composable {
        Box(contentAlignment = Alignment.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val commonModifier = Modifier.size(40.dp)
                Text(
                    text = "+",
                    color = contentColor,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = commonModifier.clickable { onPlus() }
                )
                if (quantity > 0) {
                    Text(
                        text = "$quantity",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.defaultMinSize(minWidth = 72.dp)
                    )
                    Text(
                        text = "–",
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        modifier = commonModifier.clickable { onMinus() }
                    )
                }
            }
        }
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(
            elevation = buttonElevation,
            color = containerColor,
            contentColor = contentColor,
            shape = RoundedCornerShape(percent = 50),
            content = content,
            modifier = modifier
                .animateContentSize()
                .defaultMinSize(40.dp)
                .padding(8.dp)
        )
    }
}