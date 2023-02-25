package com.abhinash.data.usecase

import com.abhinash.domain.functional.Either
import com.abhinash.domain.functional.Failure
import com.abhinash.domain.models.Product
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.AddToCartUseCase
import javax.inject.Inject

class AddToCartUseCaseImpl @Inject constructor(private val productRepository: ProductRepository) :
    AddToCartUseCase {
    override suspend fun execute(params: Product): Either<Failure, Unit> {
        return try {
            val cartProducts = productRepository.getProductsForCart().value
            val cartProduct = cartProducts.firstOrNull { it.id == params.id }
            val quantity = cartProduct?.quantity ?: 0

            productRepository.saveProductToCart(params, quantity = quantity + 1)

            Either.Right(Unit)
        } catch (t: Throwable) {
            // log failure
            Either.Left(Failure.FeatureFailure(t))
        }
    }
}