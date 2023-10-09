package dev.rsierov.feature.start

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.sierov.screen.ProductsScreen
import dev.sierov.screen.StartScreen
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
    private val navigator: Navigator,
) : Presenter<StartUiState> {

    @Composable
    override fun present(): StartUiState {

        fun eventSink(event: StartUiEvent) {
            when (event) {
                is StartUiEvent.Start -> navigator.goTo(ProductsScreen)
            }
        }

        return StartUiState(eventSink = ::eventSink)
    }
}