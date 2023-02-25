package com.abhinash.data.model

import com.abhinash.domain.models.Price
import com.google.gson.annotations.SerializedName

internal class PriceDto(
    @SerializedName("currency") val currency: String,
    @SerializedName("amount") val amount: Double
) {
    fun toDomain(): Price = Price(currency, amount/100) // Convert to cents
}
