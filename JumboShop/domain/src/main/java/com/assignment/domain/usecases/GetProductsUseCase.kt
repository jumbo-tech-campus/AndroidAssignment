package com.assignment.domain.usecases

import com.assignment.domain.entities.Product
import com.assignment.domain.repository.ProductRepository

class GetProductsUseCase constructor(private val productRepository: ProductRepository) {
    suspend fun execute(): List<Product> {
        return productRepository.getProducts()
    }
}
