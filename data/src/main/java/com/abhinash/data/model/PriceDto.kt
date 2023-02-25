package com.abhinash.data.model

import com.abhinash.domain.models.Price
import com.google.gson.annotations.SerializedName

internal class PriceDto(
    @SerializedName("currency") val currency: String,
    @SerializedName("amount") val amount: Int
) {
    fun toDomain(): Price = Price(currency, (amount/100).toDouble()) // Convert to cents
}
