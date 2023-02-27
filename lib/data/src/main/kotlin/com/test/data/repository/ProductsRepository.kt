package com.test.data.repository

import com.test.data.source.ProductsRemoteDataSource
import com.test.model.Products
import com.test.network.backend.model.mapper.NetworkResult
import com.test.network.backend.model.mapper.toProductModel
import com.test.network.backend.model.mapper.toRepositoryResult
import com.test.network.backend.model.reponse.ProductsResponse
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource
) {

    suspend fun fetchProductList(): NetworkResult<Products> =
        productsRemoteDataSource.fetchProductList()
            .toRepositoryResult(ProductsResponse::toProductModel)

}
