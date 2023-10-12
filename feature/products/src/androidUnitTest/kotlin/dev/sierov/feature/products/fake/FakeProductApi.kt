package dev.sierov.feature.products.fake

import dev.sierov.api.ProductApi
import dev.sierov.api.result.ApiResult
import dev.sierov.domain.model.category.Category
import dev.sierov.domain.model.image.ImagesPreview
import dev.sierov.domain.model.price.Currency
import dev.sierov.domain.model.price.Price
import dev.sierov.domain.model.product.Product
import dev.sierov.domain.model.product.ProductPrices
import dev.sierov.domain.model.product.ProductType
import dev.sierov.domain.model.product.UnitPrice
import dev.sierov.domain.model.unit.ItemUnit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first


/*
* Ideally should be part of a separate :api:testing module
* or even 'textFixture' that would support  sharing mocked
* resources for :api:public module consumers
*
* Leaving it here for a sake of simplicity and time-saving
*/

class FakeProductApi : ProductApi {

    private val mutableResponse = MutableSharedFlow<ApiResult<List<Product>, Unit>>()

    override suspend fun getProducts(allowCached: Boolean): ApiResult<List<Product>, Unit> =
        mutableResponse.first()

    suspend fun answerSuccess() {
        mutableResponse.emit(ApiResult.Success(response = listOf(awesomeProduct)))
    }

    suspend fun answerFailure() {
        mutableResponse.emit(ApiResult.networkFailure(RuntimeException("Connectivity.")))
    }
}

private val awesomeProductPrices = ProductPrices(
    price = Price(amount = 1234, currency = Currency("EUR")),
    unitPrice = UnitPrice(
        unit = ItemUnit.Kilogram,
        price = Price(amount = 2000, currency = Currency("EUR"))
    )
)
val awesomeProduct = Product(
    id = "product1",
    title = "Fancy Product",
    prices = awesomeProductPrices,
    available = true,
    productType = ProductType.Product,
    nixProduct = false,
    quantity = "10 pieces",
    imageInfo = ImagesPreview(primaryView = emptyList()),
    topLevelCategoryName = "Snacks",
    topLevelCategory = Category.Snacks,
)
