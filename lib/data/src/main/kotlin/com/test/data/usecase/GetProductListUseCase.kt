package com.test.data.usecase

import com.test.data.repository.ProductsRepository
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {

    suspend fun fetchProductListInfo() = productsRepository.fetchProductList()
}
