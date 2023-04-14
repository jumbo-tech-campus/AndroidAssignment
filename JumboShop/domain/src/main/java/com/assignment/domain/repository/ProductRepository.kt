package com.assignment.domain.repository

import arrow.core.Either
import com.assignment.domain.entities.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(refresh: Boolean = false): Flow<Either<Exception, List<Product>>>
    suspend fun getProductById(id: String): Product?
}
