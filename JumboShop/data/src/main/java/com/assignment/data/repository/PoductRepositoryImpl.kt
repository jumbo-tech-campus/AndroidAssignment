package com.assignment.data.repository

import android.util.Log
import arrow.core.Either
import com.assignment.data.datasource.local.product.ProductLocalDataSource
import com.assignment.data.datasource.remote.ProductRemoteDataSource
import com.assignment.data.mappers.ProductDataMapper
import com.assignment.domain.entities.Product
import com.assignment.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productLocalDataSource: ProductLocalDataSource,
) : ProductRepository {
    // TODO: Replace with query from Local Cache
    override suspend fun getProductById(id: String): Product? {
        val products = getProducts()
        return products.firstOrNull()?.getOrNull()?.find { it.id == id }
    }

    override suspend fun getProducts(): Flow<Either<Exception, List<Product>>> {
        return productLocalDataSource.getProducts()
            .map { productsDataModel ->
                try {
                    val domainProducts = ProductDataMapper.toDomain(productsDataModel)
                    Either.Right(domainProducts)
                } catch (e: Exception) {
                    Either.Left(e)
                }
            }
            .onStart {
                fetchProductsFromApi()
            }.catch {
                Either.Left(it)
            }
    }

    private suspend fun fetchProductsFromApi() {
            val apiProducts = productRemoteDataSource.getProducts().first()
            productLocalDataSource.deleteAllProducts()
            productLocalDataSource.insertProducts(apiProducts)
    }
}
