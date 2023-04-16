package com.assignment.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val prices: PricesEntity,
    val quantity: String,
    val imageInfo: ImageInfoEntity,
)
data class PricesEntity(
    val price: CurrencyAmountEntity,
    val unitPrice: UnitPriceEntity,
)
data class CurrencyAmountEntity(
    val currency: String,
    val amount: Double,
)
data class UnitPriceEntity(
    val unit: String,
    val price: CurrencyAmountEntity,
)
data class ImageInfoEntity(
    val primaryView: List<ImageSizeEntity>,
)
data class ImageSizeEntity(
    val url: String,
    val width: Int,
    val height: Int,
)
