package com.abhinash.data.usecase

import com.abhinash.domain.functional.Either
import com.abhinash.domain.functional.Failure
import com.abhinash.domain.models.Product
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.GetProductsUseCase
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(private val productRepository: ProductRepository): GetProductsUseCase {
    override suspend fun execute(params: Unit): Either<Failure, List<Product>> {
        return try{
            val products = productRepository.loadProducts()

            // log success

            Either.Right(products)
        }catch (t: Throwable){
            // log failure
            Either.Left(Failure.FeatureFailure(t))
        }
    }
}