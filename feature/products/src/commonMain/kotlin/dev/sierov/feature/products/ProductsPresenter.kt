package dev.sierov.feature.products

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.sierov.api.cause
import dev.sierov.api.result.ApiResult.Failure
import dev.sierov.api.result.ApiResult.Success
import dev.sierov.cart.Cart
import dev.sierov.cart.ShoppingContent
import dev.sierov.domain.model.product.Product
import dev.sierov.domain.usecase.GetProductsUsecase
import dev.sierov.screen.ProductsScreen
import kotlinx.coroutines.launch
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
    private val getProductsUsecase: () -> GetProductsUsecase,
    private val shoppingCart: Cart,
) : Presenter<ProductsUiState> {

    @Composable
    override fun present(): ProductsUiState {
        val coroutineScope = rememberCoroutineScope()
        var products by rememberSaveable { mutableStateOf<List<Product>>(emptyList()) }
        val shoppingContent by shoppingCart.content.collectAsState(ShoppingContent.Empty)

        val loadProducts = rememberRetained { getProductsUsecase() }
        val loadingProducts by loadProducts.inProgress.collectAsState(initial = false)

        val refreshProducts = rememberRetained { getProductsUsecase() }
        val refreshingProducts by refreshProducts.inProgress.collectAsState(initial = false)

        LaunchedEffect(Unit) {
            when (val res = loadProducts(GetProductsUsecase.Params(forceFresh = false))) {
                is Success -> products = res.response
                is Failure -> Log.e("Failed to load products", res.cause)
            }
        }

        fun eventSink(event: ProductsUiEvent) {
            when (event) {
                is ProductsUiEvent.RefreshProducts -> coroutineScope.launch {
                    when (val res = refreshProducts(GetProductsUsecase.Params(forceFresh = true))) {
                        is Success -> products = res.response
                        is Failure -> Log.e("Failed to refresh products", res.cause)
                    }
                }

                is ProductsUiEvent.AddProduct -> coroutineScope.launch {
                    shoppingCart.putProduct(event.product.id)
                }
                is ProductsUiEvent.RemoveProduct -> coroutineScope.launch {
                    shoppingCart.removeProduct(event.product.id)
                }
            }
        }

        return ProductsUiState(
            products = products,
            shoppingContent = shoppingContent,
            loading = loadingProducts,
            refreshing = refreshingProducts,
            eventSink = ::eventSink,
        )
    }
}

private val Log = Logger.withTag("ProductsPresenter")