package com.abhinash.data.usecase

import com.abhinash.domain.functional.Either
import com.abhinash.domain.functional.Failure
import com.abhinash.domain.models.CartProduct
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.RemoveFromCartUseCase
import javax.inject.Inject

class RemoveFromCartUseCaseImpl @Inject constructor(private val productRepository: ProductRepository) :
    RemoveFromCartUseCase {
    override suspend fun execute(params: CartProduct): Either<Failure, Unit> {
        return try {
            val cartProducts = productRepository.getProductsForCart().value
            val cartProduct = cartProducts.firstOrNull { it.id == params.id }

            requireNotNull(cartProduct) { "Cannot remove a product from cart that does not exist" }

            params.reduceQuantity()

            productRepository.removeProductFromCart(params)

            Either.Right(Unit)
        } catch (t: Throwable) {
            // log failure
            Either.Left(Failure.FeatureFailure(t))
        }
    }
}