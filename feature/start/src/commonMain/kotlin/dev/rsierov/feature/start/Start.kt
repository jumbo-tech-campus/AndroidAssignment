package dev.rsierov.feature.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import dev.sierov.screen.StartScreen
import me.tatarka.inject.annotations.Inject

@Inject
class StartUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is StartScreen -> ui<StartUiState> { state, modifier -> Start(state, modifier) }
        else -> null
    }
}

@Composable
fun Start(state: StartUiState, modifier: Modifier = Modifier) {
    Box(modifier) {
        val eventSink = state.eventSink
        Button(
            content = { Text(text = "Start") },
            onClick = { eventSink(StartUiEvent.Start) },
            shape = RoundedCornerShape(percent = 50),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}