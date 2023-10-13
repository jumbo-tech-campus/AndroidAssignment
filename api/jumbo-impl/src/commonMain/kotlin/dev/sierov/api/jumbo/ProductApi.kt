package dev.sierov.api.jumbo

import dev.sierov.api.BaseUrl
import dev.sierov.api.ProductApi
import dev.sierov.api.jumbo.internal.RequestExecutor
import dev.sierov.api.result.ApiResult
import dev.sierov.api.result.map
import dev.sierov.domain.model.product.Product
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Inject

@Inject
class JumboProductApi(
    private val baseUrl: BaseUrl,
    private val httpClient: HttpClient,
    private val requestExecutor: RequestExecutor,
) : ProductApi {

    override suspend fun getProducts(allowCached: Boolean): ApiResult<List<Product>, Unit> {
        @Serializable
        data class Body(val products: List<Product>)

        return requestExecutor
            .execute<Body, Unit> { httpClient.get("$baseUrl/products.json") { allowCache(allowCached) } }
            .map { it.products }
    }
}