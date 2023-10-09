package dev.sierov.feature.cart

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.sierov.screen.CartScreen
import me.tatarka.inject.annotations.Inject

@Inject
class CartPresenterFactory(
    private val presenterFactory: (Navigator) -> CartPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? = when (screen) {
        is CartScreen -> presenterFactory(navigator)
        else -> null
    }
}

@Inject
class CartPresenter : Presenter<CartUiState> {

    @Composable
    override fun present(): CartUiState {

        fun eventSink(event: CartUiEvent) {

        }

        return CartUiState(
            name = "Cart Circuit",
            eventSink = ::eventSink,
        )
    }
}