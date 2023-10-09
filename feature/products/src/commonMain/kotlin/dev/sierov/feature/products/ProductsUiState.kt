package dev.sierov.feature.products

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

data class ProductsUiState(
    val name: String = "Products",
    val eventSink: (ProductsUiEvent) -> Unit,
) : CircuitUiState

sealed class ProductsUiEvent : CircuitUiEvent {
    data object Start : ProductsUiEvent()
}