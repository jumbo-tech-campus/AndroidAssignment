package dev.sierov.feature.products

import com.slack.circuit.test.test
import dev.sierov.cart.local.LocalDatastoreCart
import dev.sierov.domain.usecase.GetProductsUsecase
import dev.sierov.feature.products.fake.FakeProductApi
import dev.sierov.feature.products.fake.awesomeProduct
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductsPresenterTest {

    private lateinit var presenter: ProductsPresenter
    private lateinit var productApi: FakeProductApi

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Before
    fun setUp() {
        productApi = FakeProductApi()
        presenter = ProductsPresenter(
            productsFilter = ProductsFilter.All,
            getProductsUsecase = { GetProductsUsecase(productApi) },
            shoppingCart = LocalDatastoreCart(temporaryFolder.root.path),
        )
    }

    @Test
    fun `loads products and manipulates cart`() = runTest {
        presenter.test {
            val initialIdling = awaitItem()
            val expectLoading = initialIdling.copy(loading = true)
            val actualLoading = awaitItem()
            assertEquals(expectLoading, actualLoading, "products are loading")

            val expectProducts = actualLoading.copy(
                loading = false,
                products = listOf(awesomeProduct)
            )
            productApi.answerSuccess()
            val actualProducts = awaitItem()
            assertEquals(expectProducts, actualProducts, "products are loaded")

            actualProducts.eventSink(ProductsUiEvent.AddProduct(awesomeProduct))
            val actualProductsInCart = awaitItem()
            val productInCart = awesomeProduct.id in actualProductsInCart.shoppingContent
            assertTrue(productInCart, "product is in the cart")

            actualProductsInCart.eventSink(ProductsUiEvent.RemoveProduct(awesomeProduct))
            val actualEmptyCart = awaitItem()
            assertTrue(actualEmptyCart.shoppingContent.isEmpty(), "no products in shopping cart")
        }
    }

    @Test
    fun `initial loading fails and content message appears`() = runTest {
        presenter.test {
            skipItems(1) // skip initial idling
            val actualLoading = awaitItem()
            val expectedContentMessage = actualLoading.copy(
                loading = false,
                contentMessage = "Please, check your connectivity."
            )
            productApi.answerFailure()
            val actualContentMessage = awaitItem()
            assertEquals(expectedContentMessage, actualContentMessage, "error message is shown")
        }
    }

    @Test
    fun `refresh products succeeds`() = runTest {
        presenter.test {
            skipItems(2) // skip idling and loading

            productApi.answerSuccess()
            val actualProducts = awaitItem()

            actualProducts.eventSink(ProductsUiEvent.RefreshProducts)
            val expectRefreshing = actualProducts.copy(refreshing = true)
            val actualRefreshing = awaitItem()
            assertEquals(expectRefreshing, actualRefreshing, "products refreshing")
            assertTrue(actualRefreshing.products.isNotEmpty(), "products are present")

            productApi.answerSuccess()
            val expectRefreshed = actualRefreshing.copy(refreshing = false)
            val actualRefreshed = awaitItem()
            assertEquals(expectRefreshed, actualRefreshed, "products refreshed")
        }
    }

    @Test
    fun `refresh products fails but shows initially loaded products`() = runTest {
        presenter.test {
            skipItems(2) // skip idling and loading

            productApi.answerSuccess()
            val actualProducts = awaitItem()

            actualProducts.eventSink(ProductsUiEvent.RefreshProducts)
            val expectRefreshing = actualProducts.copy(refreshing = true)
            val actualRefreshing = awaitItem()
            assertEquals(expectRefreshing, actualRefreshing, "products refreshing")
            assertTrue(actualRefreshing.products.isNotEmpty(), "products are present")

            productApi.answerFailure()
            val expectRefreshFailed = actualRefreshing.copy(refreshing = false)
            val actualRefreshFailed = awaitItem()
            assertEquals(expectRefreshFailed, actualRefreshFailed, "products still present")
        }
    }
}