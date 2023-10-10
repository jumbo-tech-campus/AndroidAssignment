package dev.sierov.api

import dev.sierov.api.result.ApiResult
import dev.sierov.domain.model.product.Product

interface ProductApi {
    suspend fun getProducts(): ApiResult<List<Product>, Unit>
}