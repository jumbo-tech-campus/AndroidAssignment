package dev.rsierov.feature.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.sierov.cart.ReadOnlyCart
import dev.sierov.cart.ShoppingContent
import dev.sierov.screen.ProductsScreen
import dev.sierov.screen.StartScreen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class StartPresenterFactory(
    private val presenterFactory: (Navigator) -> StartPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? = when (screen) {
        is StartScreen -> presenterFactory(navigator)
        else -> null
    }
}

@Inject
class StartPresenter(
    @Assisted private val navigator: Navigator,
    private val cart: ReadOnlyCart,
) : Presenter<StartUiState> {

    @Composable
    override fun present(): StartUiState {
        val shoppingContent by cart.content.collectAsState(ShoppingContent.Empty)

        fun eventSink(event: StartUiEvent) {
            when (event) {
                is StartUiEvent.Start -> navigator.resetRoot(ProductsScreen)
            }
        }

        return StartUiState(
            action = if (shoppingContent.isEmpty()) "Start Shopping" else "Continue Shopping",
            eventSink = ::eventSink,
        )
    }
}