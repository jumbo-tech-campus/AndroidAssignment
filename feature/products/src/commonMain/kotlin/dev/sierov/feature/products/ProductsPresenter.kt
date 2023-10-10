package dev.sierov.feature.products

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.sierov.api.ProductApi
import dev.sierov.api.result.getOrThrow
import dev.sierov.api.result.map
import dev.sierov.domain.model.product.Product
import dev.sierov.screen.ProductsScreen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ProductsPresenterFactory(
    private val presenterFactory: (Navigator) -> ProductsPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? = when (screen) {
        is ProductsScreen -> presenterFactory(navigator)
        else -> null
    }
}

@Inject
class ProductsPresenter(
    @Assisted private val navigator: Navigator,
    private val productApi: ProductApi,
) : Presenter<ProductsUiState> {

    @Composable
    override fun present(): ProductsUiState {

        val products by produceState(emptyList()) {
            value = productApi.getProducts().getOrThrow()
        }

        fun eventSink(event: ProductsUiEvent) {
            // todo: handle events
        }

        return ProductsUiState(
            products = products,
            eventSink = ::eventSink,
        )
    }
}