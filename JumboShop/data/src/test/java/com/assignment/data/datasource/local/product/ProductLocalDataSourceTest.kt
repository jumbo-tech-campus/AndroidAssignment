package com.assignment.data.datasource.local.product

import com.assignment.data.datasource.local.entities.CurrencyAmountEntity
import com.assignment.data.datasource.local.entities.ImageInfoEntity
import com.assignment.data.datasource.local.entities.ImageSizeEntity
import com.assignment.data.datasource.local.entities.PricesEntity
import com.assignment.data.datasource.local.entities.ProductEntity
import com.assignment.data.datasource.local.entities.UnitPriceEntity
import com.assignment.data.datasource.local.mappers.ProductEntityMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
class ProductLocalDataSourceTest {

    @Mock
    private lateinit var productDao: ProductDao

    private lateinit var productLocalDataSource: ProductLocalDataSource

    private val dummyProductEntity = ProductEntity(
        id = "dummy_id",
        title = "Dummy Product",
        imageInfo = ImageInfoEntity(listOf(ImageSizeEntity("https://dummy.url/image.jpg", 10,10))),
        prices = PricesEntity(
            price = CurrencyAmountEntity("EUR",10.0),
            unitPrice = UnitPriceEntity("kg", CurrencyAmountEntity("EUR",10.0) )
        ),
        quantity = "20"
    )

    @Before
    fun setup() {
        productLocalDataSource = ProductLocalDataSource(productDao)
    }

    @Test
    fun `should return products list on getProducts success`() = runTest {
        val dummyProductsList = listOf(dummyProductEntity)

        Mockito.`when`(productDao.getProducts()).thenReturn(flowOf(dummyProductsList))

        val result = productLocalDataSource.getProducts().first()
        Assert.assertEquals(ProductEntityMapper.entityListToModelDataList(dummyProductsList), result)
        Mockito.verify(productDao).getProducts()
    }

    @Test
    fun `should delete all products on deleteAllProducts success`() = runTest {
        productLocalDataSource.deleteAllProducts()
        Mockito.verify(productDao).deleteAllProducts()
    }

    @Test
    fun `should insert products on insertProducts success`() = runTest {
        val dummyProductsList = listOf(dummyProductEntity)

        productLocalDataSource.insertProducts(ProductEntityMapper.entityListToModelDataList(dummyProductsList))

        Mockito.verify(productDao).insertProducts(dummyProductsList)
    }
}

