package dev.sierov.feature.products

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ProductsPresenterFactory(
    private val presenterFactory: (Navigator) -> ProductsPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return presenterFactory(navigator)
    }
}

@Inject
class ProductsPresenter(
    @Assisted private val navigator: Navigator,
) : Presenter<ProductsUiState> {

    @Composable
    override fun present(): ProductsUiState {

        fun eventSink(event: ProductsUiEvent) {
            // todo: handle events
        }

        return ProductsUiState(
            name = "Products",
            eventSink = ::eventSink,
        )
    }
}