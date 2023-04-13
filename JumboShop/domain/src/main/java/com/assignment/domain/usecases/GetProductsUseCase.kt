package com.assignment.domain.usecases

import arrow.core.Either
import com.assignment.domain.entities.Product
import com.assignment.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase constructor(private val productRepository: ProductRepository) {
    suspend fun execute(): Flow<Either<Exception, List<Product>>> {
        return productRepository.getProducts()
    }
}
