package com.assignment.data.api

import com.assignment.data.api.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {
    @GET("main/products.json")
    suspend fun fetchProducts(): Response<ApiResponse>
}
