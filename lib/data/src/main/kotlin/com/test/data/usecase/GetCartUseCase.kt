package com.test.data.usecase

import com.test.data.repository.ProductsLocalRepository
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val productsLocalRepository: ProductsLocalRepository
) {

    suspend fun getCart() = productsLocalRepository.getCart()
}
