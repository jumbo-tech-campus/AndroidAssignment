package dev.sierov.feature.products

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.sierov.api.ProductApi
import dev.sierov.api.result.getOrThrow
import dev.sierov.domain.model.product.Product
import dev.sierov.screen.ProductsScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
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

        val refreshTrigger = remember { MutableSharedFlow<Cache>() }
        val productsFlow = remember { refreshTrigger.obtainProducts() }
        val products by productsFlow.collectAsState(emptyList())

        LaunchedEffect(Unit) {
            refreshTrigger.emit(Cache.Allowed)
        }

        fun eventSink(event: ProductsUiEvent) {
            when (event) {
                is ProductsUiEvent.RefreshProducts -> refreshTrigger.tryEmit(Cache.NotAllowed)
                is ProductsUiEvent.LoadProducts -> refreshTrigger.tryEmit(Cache.Allowed)
                is ProductsUiEvent.AddProduct -> TODO()
                is ProductsUiEvent.RemoveProduct -> TODO()
            }
        }

        return ProductsUiState(
            products = products,
            loading = false,
            refreshing = false,
            eventSink = ::eventSink,
        )
    }

    private fun Flow<Cache>.obtainProducts(): Flow<List<Product>> =
        map { productApi.getProducts(allowCached = it == Cache.Allowed).getOrThrow() }

    private enum class Cache {
        Allowed, NotAllowed
    }
}