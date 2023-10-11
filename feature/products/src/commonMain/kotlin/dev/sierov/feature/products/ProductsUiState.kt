package dev.sierov.feature.products

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import dev.sierov.cart.ShoppingContent
import dev.sierov.domain.model.product.Product

@Stable
data class ProductsUiState(
    val products: List<Product>,
    val shoppingContent: ShoppingContent,
    val loading: Boolean,
    val refreshing: Boolean,
    val contentMessage: String?,
    val eventSink: (ProductsUiEvent) -> Unit,
) : CircuitUiState

@Immutable
sealed class ProductsUiEvent : CircuitUiEvent {
    data object RefreshProducts : ProductsUiEvent()
    data class AddProduct(val product: Product) : ProductsUiEvent()
    data class RemoveProduct(val product: Product) : ProductsUiEvent()
}