package com.test.data.usecases

import com.test.data.repository.ProductsLocalRepository
import com.test.data.returnMockedProductResponse
import com.test.data.source.ProductsLocalDataSource
import com.test.data.usecase.SaveProductUseCase
import com.test.model.CartItem
import com.test.network.backend.model.mapper.toProductModel
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
class SaveProductUseCaseTest {

    private val productsDB: ProductsDb = mock()
    private val productsDao: ProductsDao = mock()
    private val productsLocalDataSource = ProductsLocalDataSource(productsDB)
    private val productsLocalRepository = ProductsLocalRepository(productsLocalDataSource)
    private val saveProductUseCase = SaveProductUseCase(productsLocalRepository)
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
    fun testSaveCartItemSuccessfully() {
        runTest {
            whenever(productsDao.insertOrUpdate(mockedCartItemEntity))
                .thenReturn(1)
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().insertOrUpdate(mockedCartItemEntity))
                .thenReturn(1)
            whenever(productsLocalDataSource.saveCartItem(mockedCartItemEntity))
                .thenReturn(1)

            val response = saveProductUseCase.saveCartItem(mockedCartItem)
            Assert.assertEquals(response, 1)
        }
    }

    @Test
    fun testSaveProductSuccessfully() {
        runTest {
            whenever(productsDao.insertOrUpdate(mockedCartItemEntity))
                .thenReturn(1)
            whenever(productsDB.productDao())
                .thenReturn(productsDao)
            whenever(productsDB.productDao().insertOrUpdate(mockedCartItemEntity))
                .thenReturn(1)
            whenever(productsLocalDataSource.saveCartItem(mockedCartItemEntity))
                .thenReturn(1)
            whenever(
                productsLocalRepository.saveProduct(
                    returnMockedProductResponse().toProductModel(), 3
                )
            )
                .thenReturn(1)

            val response = saveProductUseCase.saveProduct(
                returnMockedProductResponse().toProductModel(), 3
            )
            Assert.assertEquals(response, 1)
        }
    }
}