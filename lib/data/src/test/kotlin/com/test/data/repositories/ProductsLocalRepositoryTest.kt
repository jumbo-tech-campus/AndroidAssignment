package com.test.data.repositories

import com.test.data.repository.ProductsLocalRepository
import com.test.data.returnMockedProductResponse
import com.test.data.source.ProductsLocalDataSource
import com.test.network.backend.model.mapper.toProductModel
import com.test.network.local.ProductsDb
import com.test.network.local.dao.ProductsDao
import com.test.network.local.entities.CartItemEntity
import com.test.network.local.mapper.toCartItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ProductsLocalRepositoryTest {

    private val productsDB: ProductsDb = mock()
    private val productsDao: ProductsDao = mock()
    private val productsLocalDataSource = ProductsLocalDataSource(productsDB)
    private val productsLocalRepository = ProductsLocalRepository(productsLocalDataSource)
    private val mockedCartItem = CartItemEntity(
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
                .thenReturn(listOf(mockedCartItem))
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().getCart())
                .thenReturn(listOf(mockedCartItem))
            whenever(productsLocalDataSource.getCart())
                .thenReturn(listOf(mockedCartItem))

            val response = productsLocalRepository.getCart()
            assertEquals(response.size, 1)
            assertEquals(response.first().name, mockedCartItem.name)

        }
    }

    @Test
    fun testUpdateCartItemSuccessfully() {
        runTest {
            whenever(productsDao.deleteOrUpdate(mockedCartItem))
                .thenReturn(1)
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().deleteOrUpdate(mockedCartItem))
                .thenReturn(1)
            whenever(productsLocalDataSource.updateProduct(mockedCartItem))
                .thenReturn(1)

            val response = productsLocalRepository.updateCart(mockedCartItem.toCartItemModel())
            assertEquals(response, 1)
        }
    }

    @Test
    fun testSaveCartItemSuccessfully() {
        runTest {
            whenever(productsDao.insertOrUpdate(mockedCartItem))
                .thenReturn(2)
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().insertOrUpdate(mockedCartItem))
                .thenReturn(2)
            whenever(productsLocalDataSource.saveCartItem(mockedCartItem))
                .thenReturn(2)

            val response = productsLocalRepository.saveCartItem(mockedCartItem.toCartItemModel())
            assertEquals(response, 2)
        }
    }

    @Test
    fun testSaveProductSuccessfully() {
        runTest {
            whenever(productsDao.insertOrUpdate(mockedCartItem))
                .thenReturn(1)
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().insertOrUpdate(mockedCartItem))
                .thenReturn(1)
            whenever(productsLocalDataSource.saveCartItem(mockedCartItem))
                .thenReturn(1)

            val response = productsLocalRepository.saveProduct(
                returnMockedProductResponse().toProductModel(), 5
            )
            assertEquals(response, 1)
        }
    }

    @Test
    fun testDeleteCartItemSuccessfully() {
        runTest {
            val mockedCartItem = CartItemEntity(
                uid = "1", "Bread",
                "image/test.url",
                230, "EUR", 1
            )
            whenever(productsDao.deleteOrUpdate(mockedCartItem))
                .thenReturn(0)
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().deleteOrUpdate(mockedCartItem))
                .thenReturn(0)
            whenever(productsLocalDataSource.updateProduct(mockedCartItem))
                .thenReturn(0)

            val response = productsLocalRepository.updateCart(mockedCartItem.toCartItemModel())
            assertEquals(response, 0)
        }
    }
}
