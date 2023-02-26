package com.test.network.mappers

import com.test.network.backend.model.mapper.toProductModel
import org.junit.Assert
import org.junit.Test

class ProductsApiResponseMappersTest {
    @Test
    fun mapProductListSuccessfully() {
        val mockedInfo = returnMockedProductsResponse().toProductModel()
        Assert.assertEquals(mockedInfo, mockedInfo)
    }
}