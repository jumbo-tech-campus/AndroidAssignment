package dev.rsierov.feature.start

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState

@Stable
data class StartUiState(
    val action: String,
    val eventSink: (StartUiEvent) -> Unit,
) : CircuitUiState

@Immutable
sealed class StartUiEvent : CircuitUiEvent {
    data object Start : StartUiEvent()
}
