package com.abhinash.domain.models

data class Product(
    val id: String,
    val title: String,
    val productPrices: ProductPrices,
    val productImages: List<ProductImage>,
    val quantity: String
)

