package com.test.data.usecases

import com.test.data.repository.ProductsLocalRepository
import com.test.data.source.ProductsLocalDataSource
import com.test.data.usecase.GetCartUseCase
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
class GetCartUseCaseTest {

    private val productsDB: ProductsDb = mock()
    private val productsDao: ProductsDao = mock()
    private val productsLocalDataSource = ProductsLocalDataSource(productsDB)
    private val productsLocalRepository = ProductsLocalRepository(productsLocalDataSource)
    private val getCartUseCaseTest = GetCartUseCase(productsLocalRepository)
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
    fun testGetCartSuccessfully() {
        runTest {
            whenever(productsDao.getCart())
                .thenReturn(listOf(mockedCartItemEntity))
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().getCart())
                .thenReturn(listOf(mockedCartItemEntity))
            whenever(productsLocalDataSource.getCart())
                .thenReturn(listOf(mockedCartItemEntity))

            val response = getCartUseCaseTest.getCart()
            Assert.assertEquals(response.size, 1)
            Assert.assertEquals(response.first().name, mockedCartItem.name)

        }
    }
}