package com.abhinash.domain.models

data class Product(
    val id: String,
    val title: String,
    val productPrices: ProductPrices,
    val productImage: ProductImage,
    val quantity: String
)

