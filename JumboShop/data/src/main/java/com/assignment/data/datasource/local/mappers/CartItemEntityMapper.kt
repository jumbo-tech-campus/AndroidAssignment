package com.assignment.data.datasource.local.mappers

import com.assignment.data.datasource.local.entities.CartItemEntity
import com.assignment.data.models.CartItemDataModel

object CartItemEntityMapper {
    fun entityToModelData(cartItemEntity: CartItemEntity): CartItemDataModel {
        return CartItemDataModel(
            id = cartItemEntity.id,
            title = cartItemEntity.title,
            imageUrl = cartItemEntity.imageUrl,
            price = cartItemEntity.price,
            currency = cartItemEntity.currency,
            quantity = cartItemEntity.quantity,
        )
    }

    fun modelToEntity(cartItem: CartItemDataModel): CartItemEntity {
        return CartItemEntity(
            id = cartItem.id,
            title = cartItem.title,
            imageUrl = cartItem.imageUrl,
            price = cartItem.price,
            currency = cartItem.currency,
            quantity = cartItem.quantity,
        )
    }

    fun entityListToModelDataList(cartItemEntities: List<CartItemEntity>): List<CartItemDataModel> {
        return cartItemEntities.map { entityToModelData(it) }
    }

    fun modelListToEntityList(cartItems: List<CartItemDataModel>): List<CartItemEntity> {
        return cartItems.map { modelToEntity(it) }
    }
}
