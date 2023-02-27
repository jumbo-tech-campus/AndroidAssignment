package com.abhinash.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhinash.domain.models.CartProduct

@Entity(tableName = "cart_products")
class CartProductStorageModel(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "unt_price_in_eur") val unitPriceInEur: Double,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "url") val image: String,
    @ColumnInfo(name = "size") val size: Int
){
    fun toDomain(): CartProduct =
        CartProduct.load(
            id = id,
            title = title,
            unitPriceInEur = unitPriceInEur,
            quantity = quantity,
            image = image,
            size = size
        )

    companion object{
        fun fromDomain(cartProduct: CartProduct): CartProductStorageModel =
            CartProductStorageModel(
                id = cartProduct.id,
                title = cartProduct.title,
                unitPriceInEur = cartProduct.unitPriceInEur,
                quantity = cartProduct.quantity,
                image = cartProduct.image,
                size = cartProduct.size
            )
    }
}