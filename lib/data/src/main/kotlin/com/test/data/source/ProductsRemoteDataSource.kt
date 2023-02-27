package com.test.data.source

import com.test.network.backend.service.ProductsApi
import javax.inject.Inject

class ProductsRemoteDataSource @Inject constructor(
    private val productsApi: ProductsApi
) {
    suspend fun fetchProductList() = productsApi.fetchProductList()
}
