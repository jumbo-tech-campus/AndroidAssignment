package com.assignment.domain.entities

data class Product(
    val id: String,
    val title: String,
    val prices: Prices,
    val quantity: String,
    val imageInfo: ImageInfo,
)

data class Prices(
    val price: CurrencyAmount,
    val unitPrice: UnitPrice
)

data class CurrencyAmount(
    val currency: String,
    val amount: Double
)

data class UnitPrice(
    val unit: String,
    val price: CurrencyAmount
)

data class ImageInfo(
    val primaryView: List<ImageSize>
)

data class ImageSize(
    val url: String,
    val width: Int,
    val height: Int
)
