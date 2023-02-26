package com.test.network.local.mapper

import com.test.model.Cart
import com.test.model.Product
import com.test.network.local.entities.CartEntity

fun Product.toCartEntity(quantity: Int) = CartEntity(
    name = this.title,
    image = this.imageInfo.primaryView.first().url,
    price = this.prices.price.amount,
    currency = this.prices.price.currency,
    quantity = quantity
)

fun CartEntity.toCartModel() = Cart(
    name = this.name,
    image = this.image,
    price = this.price,
    currency = this.currency,
    quantity = this.quantity
)

fun Cart.toCartEntity() = CartEntity(
    name = this.name,
    image = this.image,
    price = this.price,
    currency = this.currency,
    quantity = this.quantity
)

fun List<CartEntity>.toCartListModel(): List<Cart> {
    val listResult = mutableListOf<Cart>()
    this.forEach {
        listResult.add(
            it.toCartModel()
        )
    }
    return listResult.toList()
}
