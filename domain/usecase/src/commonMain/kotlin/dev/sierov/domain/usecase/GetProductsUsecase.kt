package dev.sierov.domain.usecase

import dev.sierov.api.ProductApi
import dev.sierov.api.result.ApiResult
import dev.sierov.domain.model.product.Product
import dev.sierov.domain.usecase.internal.StatefulUsecase
import kotlinx.coroutines.delay
import me.tatarka.inject.annotations.Inject

@Inject
class GetProductsUsecase(
    private val productApi: ProductApi,
) : StatefulUsecase<GetProductsUsecase.Params, ApiResult<List<Product>, Unit>>() {

    data class Params(val forceFresh: Boolean)

    override suspend fun doWork(params: Params): ApiResult<List<Product>, Unit> {
        if (params.forceFresh) delay(1_000) // it's too quick to notice
        return productApi.getProducts(allowCached = !params.forceFresh)
    }
}