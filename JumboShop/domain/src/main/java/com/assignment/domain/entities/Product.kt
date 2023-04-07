package com.assignment.domain.entities

import java.math.BigDecimal

data class Product(
    val id: String,
    val title: String,
    val prices: Prices,
    val available: Boolean,
    val productType: String,
    val nixProduct: Boolean,
    val quantity: String,
    val imageInfo: ImageInfo,
    val topLevelCategory: String,
    val topLevelCategoryId: String
)

data class Prices(
    val price: CurrencyAmount,
    val unitPrice: UnitPrice
)

data class CurrencyAmount(
    val currency: String,
    val amount: BigDecimal
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
