package com.assignment.data.models

data class ProductDataModel(
    val id: String,
    val title: String,
    val prices: PricesDataModel,
    val quantity: String,
    val imageInfo: ImageInfoDataModel,
)
data class PricesDataModel(
    val price: CurrencyAmountDataModel,
    val unitPrice: UnitPriceDataModel,
)
data class CurrencyAmountDataModel(
    val currency: String,
    val amount: Double,
)
data class UnitPriceDataModel(
    val unit: String,
    val price: CurrencyAmountDataModel,
)
data class ImageInfoDataModel(
    val primaryView: List<ImageSizeDataModel>,
)
data class ImageSizeDataModel(
    val url: String,
    val width: Int,
    val height: Int,
)
