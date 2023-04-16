package com.assignment.domain.usecases

import arrow.core.Either
import com.assignment.domain.entities.Product
import com.assignment.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase constructor(private val productRepository: ProductRepository) {
    suspend fun execute(refresh: Boolean): Flow<Either<Exception, List<Product>>> {
        return productRepository.getProducts(refresh)
    }
}
