package dev.sierov.domain.model.product

import dev.sierov.core.parcel.Parcelable
import dev.sierov.core.parcel.Parcelize
import dev.sierov.domain.model.category.Category
import dev.sierov.domain.model.image.ImagesPreview
import dev.sierov.domain.model.price.Price
import dev.sierov.domain.model.unit.ItemUnit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Parcelize
@Serializable
data class Product(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("prices") val prices: ProductPrices,
    @SerialName("available") val available: Boolean,
    @SerialName("productType") val productType: ProductType,
    @SerialName("nixProduct") val nixProduct: Boolean,
    @SerialName("quantity") val quantity: String,
    @SerialName("imageInfo") val imageInfo: ImagesPreview,
    @SerialName("topLevelCategory") val topLevelCategoryName: String,
    @SerialName("topLevelCategoryId") val topLevelCategory: Category,
) : Parcelable

@Parcelize
@Serializable
data class ProductPrices(
    @SerialName("price") val price: Price,
    @SerialName("unitPrice") val unitPrice: UnitPrice,
) : Parcelable

@Parcelize
@Serializable
data class UnitPrice(
    @SerialName("unit") val unit: ItemUnit,
    @SerialName("price") val price: Price,
) : Parcelable

@JvmInline
@Parcelize
@Serializable
value class ProductType(val name: String) : Parcelable {
    companion object {
        val Product = ProductType(name = "Product")
        val PartOfRetailSet = ProductType(name = "PartOfRetailSet")
    }
}