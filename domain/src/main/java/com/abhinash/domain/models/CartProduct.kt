package com.abhinash.domain.models


class CartProduct private constructor(
    val id: String,
    val title: String,
    val unitPriceInEur: Double,
    var quantity: Int,
    val image: String,
    val size: Int
) {

    fun reduceQuantity(){
        quantity -= 1
    }
    companion object {
        fun load(
            id: String,
            title: String,
            unitPriceInEur: Double,
            quantity: Int,
            image: String,
            size: Int
        ): CartProduct =
            CartProduct(
                id = id,
                title = title,
                unitPriceInEur = unitPriceInEur,
                quantity = quantity,
                image = image,
                size = size
            )

        fun fromProduct(product: Product, quantity: Int): CartProduct =
            CartProduct(
                id = product.id,
                title = product.title,
                unitPriceInEur = product.productPrices.price.amount * quantity,
                quantity = quantity,
                image = product.productImage.url,
                size = product.productImage.size
            )
    }
}