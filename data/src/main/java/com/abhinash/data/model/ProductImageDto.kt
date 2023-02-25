package com.abhinash.data.model

import com.abhinash.domain.models.ProductImage
import com.google.gson.annotations.SerializedName

internal class ProductImageDto(
    @SerializedName("primaryView") val primaryView: List<ImagePrimaryViewDto>
) {
    fun toDomain(): ProductImage =
        ProductImage(primaryView.first().height, primaryView.first().url)
}

internal class ImagePrimaryViewDto(
    @SerializedName("height") val height: Int,
    @SerializedName("url") val url: String
)