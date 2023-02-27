package com.abhinash.data.model

import com.abhinash.domain.models.Product
import com.google.gson.annotations.SerializedName

internal class ProductDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("prices") val productPrices: ProductPricesDto,
    @SerializedName("imageInfo") val productImages: ProductImageDto,
    @SerializedName("quantity") val quantity: String
){
    fun toDomain(): Product =
        Product(id,
            title,
            productPrices.toDomain(),
            productImages.toDomain(),
            quantity)
}