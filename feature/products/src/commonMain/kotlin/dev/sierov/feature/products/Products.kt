@file:OptIn(ExperimentalMaterialApi::class)

package dev.sierov.feature.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        modifier = modifier
    )
}

@Composable
fun Products(
    state: ProductsUiState,
    onRefreshProducts: () -> Unit,
    onAddProduct: (Product) -> Unit,
    onRemoveProduct: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = onRefreshProducts,
    )
    Box(modifier.pullRefresh(pullRefreshState)) {
        LazyColumn {
            items(state.products) { product ->
                Text(text = product.title, modifier = Modifier)
            }
        }
        PullRefreshIndicator(
            refreshing = state.refreshing,
            state = pullRefreshState,
        )
    }
}