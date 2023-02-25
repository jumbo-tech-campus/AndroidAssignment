package com.abhinash.data.usecase

import com.abhinash.domain.functional.Either
import com.abhinash.domain.functional.Failure
import com.abhinash.domain.models.CartProduct
import com.abhinash.domain.repository.ProductRepository
import com.abhinash.domain.usecase.GetCartProducts
import javax.inject.Inject

class GetCartProductsImpl  @Inject constructor(private val productRepository: ProductRepository): GetCartProducts {
    override suspend fun execute(params: Unit): Either<Failure, List<CartProduct>>
    {
        return try{
            val productsCount = productRepository.getProductsForCart().value

            // log success

            Either.Right(productsCount)
        }catch (t: Throwable){
            // log failure
            Either.Left(Failure.FeatureFailure(t))
        }
    }
}