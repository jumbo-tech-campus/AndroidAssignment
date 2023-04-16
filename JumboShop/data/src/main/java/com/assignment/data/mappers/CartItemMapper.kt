package com.assignment.data.mappers

import com.assignment.data.models.CartItemDataModel
import com.assignment.domain.entities.CartItem

object CartItemMapper {
    fun dataToDomainModel(dataCartItem: CartItemDataModel): CartItem {
        return CartItem(
            id = dataCartItem.id,
            title = dataCartItem.title,
            imageUrl = dataCartItem.imageUrl,
            price = dataCartItem.price,
            currency = dataCartItem.currency,
            quantity = dataCartItem.quantity,
        )
    }

    fun domainToDataModel(cartItem: CartItem): CartItemDataModel {
        return CartItemDataModel(
            id = cartItem.id,
            title = cartItem.title,
            imageUrl = cartItem.imageUrl,
            price = cartItem.price,
            currency = cartItem.currency,
            quantity = cartItem.quantity,
        )
    }

    fun dataListToDomainModelList(dataCartItems: List<CartItemDataModel>): List<CartItem> {
        return dataCartItems.map { dataToDomainModel(it) }
    }

    fun domainListToDataModelList(domainCartItems: List<CartItem>): List<CartItemDataModel> {
        return domainCartItems.map { domainToDataModel(it) }
    }
}
