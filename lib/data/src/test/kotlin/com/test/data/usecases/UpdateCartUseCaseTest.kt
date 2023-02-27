package com.test.data.usecases

import com.test.data.repository.ProductsLocalRepository
import com.test.data.source.ProductsLocalDataSource
import com.test.data.usecase.UpdateCartUseCase
import com.test.model.CartItem
import com.test.network.local.ProductsDb
import com.test.network.local.dao.ProductsDao
import com.test.network.local.entities.CartItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class UpdateCartUseCaseTest {

    private val productsDB: ProductsDb = mock()
    private val productsDao: ProductsDao = mock()
    private val productsLocalDataSource = ProductsLocalDataSource(productsDB)
    private val productsLocalRepository = ProductsLocalRepository(productsLocalDataSource)
    private val updateCartUseCase = UpdateCartUseCase(productsLocalRepository)
    private val mockedCartItem = CartItem(
        id = "1", "Bread",
        "image/test.url",
        230, "EUR", 5
    )

    private val mockedCartItemEntity = CartItemEntity(
        uid = "1", "Bread",
        "image/test.url",
        230, "EUR", 5
    )


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testUpdateCartSuccessfully() {
        runTest {
            whenever(productsDao.deleteOrUpdate(mockedCartItemEntity))
                .thenReturn(1)
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().deleteOrUpdate(mockedCartItemEntity))
                .thenReturn(1)
            whenever(productsLocalDataSource.updateProduct(mockedCartItemEntity))
                .thenReturn(1)

            val response = updateCartUseCase.updateCart(mockedCartItem)
            Assert.assertEquals(response, 1)
        }
    }

    @Test
    fun testDeleteCartSuccessfully() {
        val mockedCartItem = CartItem(
            id = "1", "Bread",
            "image/test.url",
            230, "EUR", 1
        )

        val mockedCartItemEntity = CartItemEntity(
            uid = "1", "Bread",
            "image/test.url",
            230, "EUR", 1
        )
        runTest {
            whenever(productsDao.deleteOrUpdate(mockedCartItemEntity))
                .thenReturn(1)
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().deleteOrUpdate(mockedCartItemEntity))
                .thenReturn(1)
            whenever(productsLocalDataSource.updateProduct(mockedCartItemEntity))
                .thenReturn(1)

            val response = updateCartUseCase.updateCart(mockedCartItem)
            Assert.assertEquals(response, 1L)
        }
    }
}