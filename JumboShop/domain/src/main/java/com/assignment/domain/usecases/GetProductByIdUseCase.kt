package com.assignment.domain.usecases

import com.assignment.domain.entities.Product
import com.assignment.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun execute(id: String): Product? {
        return productRepository.getProductById(id)
    }
}
