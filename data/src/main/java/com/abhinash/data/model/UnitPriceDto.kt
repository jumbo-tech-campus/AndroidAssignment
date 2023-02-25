package com.abhinash.data.model

import com.abhinash.domain.models.UnitPrice
import com.google.gson.annotations.SerializedName

internal class UnitPriceDto(
    @SerializedName("unit") val unit: String,
    @SerializedName("price") val price: PriceDto
) {
    fun toDomain(): UnitPrice =
        UnitPrice(
            unit,
            price.toDomain()
        )
}
