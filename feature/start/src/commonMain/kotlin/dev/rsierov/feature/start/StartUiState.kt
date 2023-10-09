package dev.rsierov.feature.start

import androidx.compose.runtime.Immutable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Immutable data class StartUiState(
    val eventSink: (StartUiEvent) -> Unit,
) : CircuitUiState

sealed class StartUiEvent : CircuitUiEvent {
    data object Start : StartUiEvent()
}
