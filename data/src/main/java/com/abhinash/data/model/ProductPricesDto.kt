package com.abhinash.data.model

import com.abhinash.domain.models.ProductPrices
import com.google.gson.annotations.SerializedName

internal class ProductPricesDto(
    @SerializedName("price") val price: PriceDto,
    @SerializedName("unitPrice") val unitPrice: UnitPriceDto
) {

    fun toDomain(): ProductPrices =
        ProductPrices(
            price.toDomain(),
            unitPrice.toDomain()
        )
}
