package dev.sierov.feature.cart

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiState

@Immutable
data class CartUiState(
    val name: String = "Cart",
    val eventSink: (CartUiEvent) -> Unit,
) : CircuitUiState

sealed class CartUiEvent {
    data object Start : CartUiEvent()
}