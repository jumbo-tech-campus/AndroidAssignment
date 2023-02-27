package com.test.network.backend.service

import com.test.model.Product
import com.test.network.backend.model.NetworkResponse
import com.test.network.backend.model.reponse.ProductsResponse
import retrofit2.http.GET

interface ProductsApi {

    @GET("products.json")
    suspend fun fetchProductList(
    ): NetworkResponse<ProductsResponse, Product>
}
