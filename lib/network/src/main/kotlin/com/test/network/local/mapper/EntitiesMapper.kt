package com.test.network.local.mapper

import com.test.model.CartItem
import com.test.model.Product
import com.test.network.local.entities.CartItemEntity

fun Product.toCartItemEntity(quantity: Int) = CartItemEntity(
    uid = this.id,
    name = this.title,
    image = this.imageInfo.primaryView.first().url,
    price = this.prices.price.amount,
    currency = this.prices.price.currency,
    quantity = quantity
)

fun CartItemEntity.toCartItemModel() = CartItem(
    id = this.uid,
    name = this.name,
    image = this.image,
    price = this.price,
    currency = this.currency,
    quantity = this.quantity
)

fun CartItem.toCartItemEntity() = CartItemEntity(
    uid = this.id,
    name = this.name,
    image = this.image,
    price = this.price,
    currency = this.currency,
    quantity = this.quantity
)

fun List<CartItemEntity>.toCartItemListModel(): List<CartItem> {
    val listResult = mutableListOf<CartItem>()
    this.forEach {
        listResult.add(
            it.toCartItemModel()
        )
    }
    return listResult.toList()
}
