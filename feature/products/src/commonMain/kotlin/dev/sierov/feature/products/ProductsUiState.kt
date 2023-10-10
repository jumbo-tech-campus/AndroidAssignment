package dev.sierov.feature.products

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import dev.sierov.domain.model.product.Product

data class ProductsUiState(
    val products: List<Product>,
    val eventSink: (ProductsUiEvent) -> Unit,
) : CircuitUiState

sealed class ProductsUiEvent : CircuitUiEvent {
    data object Start : ProductsUiEvent()
}