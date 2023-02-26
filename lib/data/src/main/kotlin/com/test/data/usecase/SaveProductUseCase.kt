package com.test.data.usecase

import com.test.data.repository.ProductsLocalRepository
import com.test.model.Product
import javax.inject.Inject

class SaveProductUseCase @Inject constructor(
    private val productsLocalRepository: ProductsLocalRepository
) {

    suspend fun saveProduct(product: Product, quantity: Int) =
        productsLocalRepository.saveProduct(product, quantity)
}
