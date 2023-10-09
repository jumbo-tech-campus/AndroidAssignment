package dev.sierov.feature.root

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.runtime.Navigator
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias AppContent = @Composable (
    backstack: SaveableBackStack,
    navigator: Navigator,
    onOpenUrl: (String) -> Unit,
    modifier: Modifier,
) -> Unit

@Inject
@Composable
fun AppContent(
    @Assisted backstack: SaveableBackStack,
    @Assisted navigator: Navigator,
    @Assisted onOpenUrl: (String) -> Unit,
    circuit: Circuit,
    @Assisted modifier: Modifier = Modifier,
) {
    CircuitCompositionLocals(circuit) {
        MaterialTheme {
            // TODO() add Root composable
        }
    }
}