package dev.sierov.feature.products

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import dev.sierov.domain.model.product.Product

@Stable
data class ProductsUiState(
    val products: List<Product>,
    val loading: Boolean,
    val refreshing: Boolean,
    val eventSink: (ProductsUiEvent) -> Unit,
) : CircuitUiState

@Immutable
sealed class ProductsUiEvent : CircuitUiEvent {
    data object RefreshProducts : ProductsUiEvent()
    class AddProduct(val product: Product) : ProductsUiEvent()
    class RemoveProduct(val product: Product) : ProductsUiEvent()
}