package com.assignment.data.datasource.local.cart

import com.assignment.data.datasource.local.entities.CartItemEntity
import com.assignment.data.datasource.local.mappers.CartItemEntityMapper
import com.assignment.data.models.CartItemDataModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CartLocalDataSourceTest {

    private lateinit var cartDao: CartDao
    private lateinit var cartLocalDataSource: CartLocalDataSource

    @Before
    fun setUp() {
        cartDao = mock(CartDao::class.java)
        cartLocalDataSource = CartLocalDataSource(cartDao)
    }

    @Test
    fun `should return cart item data models on get cart items`() = runBlocking {
        val cartItemEntities = listOf(
            CartItemEntity(
                id = "1",
                title = "Dummy Item",
                imageUrl = "https://dummy.url/image.jpg",
                price = 10.0,
                currency = "EUR",
                quantity = 6
            ),
            CartItemEntity(
                id = "2",
                title = "Dummy Item",
                imageUrl = "https://dummy.url/image.jpg",
                price = 10.0,
                currency = "EUR",
                quantity = 8
            )
        )
        val cartItemDataModels = CartItemEntityMapper.entityListToModelDataList(cartItemEntities)
        `when`(cartDao.getCartItems()).thenReturn(flow { emit(cartItemEntities) })

        val result = cartLocalDataSource.getCartItems().toList().first()

        assertEquals(cartItemDataModels, result)
    }

    @Test
    fun `should add cart item to cart`() = runBlocking {
        val cartItemDataModel = CartItemDataModel(
            id = "dummy_item_id",
            title = "Dummy Item",
            imageUrl = "https://dummy.url/image.jpg",
            price = 15.0,
            currency = "EUR",
            quantity = 5
        )
        val cartItemEntity = CartItemEntityMapper.modelToEntity(cartItemDataModel)

        cartLocalDataSource.addToCart(cartItemDataModel)

        Mockito.verify(cartDao).addToCart(cartItemEntity)
    }

    @Test
    fun `should increment cart item quantity`() = runBlocking {
        val itemId = "testItemId"

        cartLocalDataSource.incrementCartItem(itemId)

        Mockito.verify(cartDao).incrementCartItem(itemId)
    }

    @Test
    fun `should decrement cart item quantity or delete cart item`() = runBlocking {
        val itemId = "testItemId"
        val cartItem = CartItemEntity(
            id = itemId,
            title = "Dummy Item",
            imageUrl = "https://dummy.url/image.jpg",
            price = 10.0,
            currency = "EUR",
            quantity = 3
        )
        `when`(cartDao.getCartItem(itemId)).thenReturn(cartItem)

        cartLocalDataSource.decrementCartItem(itemId)

        if (cartItem.quantity > 1) {
            Mockito.verify(cartDao).decrementCartItem(itemId)
        } else {
            Mockito.verify(cartDao).deleteCartItem(itemId)
        }
    }

    @Test
    fun `should delete cart item`() = runBlocking {
        val itemId = "sample_item_id"

        cartLocalDataSource.deleteCartItem(itemId)

        Mockito.verify(cartDao).deleteCartItem(itemId)
    }

    @Test
    fun `should clear all cart items`() = runBlocking {
        cartLocalDataSource.clearCart()

        Mockito.verify(cartDao).deleteAllCartItems()
    }
}
