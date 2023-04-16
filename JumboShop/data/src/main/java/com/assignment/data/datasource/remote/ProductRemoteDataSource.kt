package com.assignment.data.datasource.remote

import com.assignment.data.api.ProductResponseMapper
import com.assignment.data.api.ProductService
import com.assignment.data.models.ProductDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(private val productService: ProductService) {
    suspend fun getProducts(): Flow<List<ProductDataModel>> = flow {
        val response = productService.fetchProducts()
        if (response.isSuccessful) {
            val products = response.body()?.products ?: emptyList()
            emit(ProductResponseMapper.toDataModel(products))
        } else {
            throw Exception("Failed to fetch products")
        }
    }
}
