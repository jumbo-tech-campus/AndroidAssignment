package dev.sierov.feature.products

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui

class ProductsUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return ui<ProductsUiState> { state, modifier -> Products(state, modifier) }
    }
}

@Composable
fun Products(state: ProductsUiState, modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(text = state.name, modifier = Modifier.align(Alignment.Center))
    }
}