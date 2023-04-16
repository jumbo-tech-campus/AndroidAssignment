package com.assignment.data.datasource.remote

import com.assignment.data.api.ProductResponseMapper
import com.assignment.data.api.ProductService
import com.assignment.data.api.models.ApiResponse
import com.assignment.data.api.models.ProductResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRemoteDataSourceTest {

    private lateinit var productService: ProductService
    private lateinit var productRemoteDataSource: ProductRemoteDataSource

    @Before
    fun setUp() {
        productService = mock(ProductService::class.java)
        productRemoteDataSource = ProductRemoteDataSource(productService)
    }

    @Test
    fun `should return product data models on fetch products success`() = runTest {
        val products = listOf<ProductResponse>(
            // add some sample ProductResponse instances here
        )
        val apiResponse = ApiResponse(products = products)
        val response = Response.success(apiResponse)
        `when`(productService.fetchProducts()).thenReturn(response)

        val result = productRemoteDataSource.getProducts().toList().first()

        assertEquals(products.map { ProductResponseMapper.toDataModel(it) }, result)
    }

    @Test
    fun `should throw exception with message on fetch products failure`() = runTest {
        val errorResponse = Response.error<ApiResponse>(500,
            "Internal Server Error".toResponseBody(null)
        )
        `when`(productService.fetchProducts()).thenReturn(errorResponse)

        val exception = try {
            productRemoteDataSource.getProducts().toList()
            null
        } catch (e: Exception) {
            e
        }

        assertEquals("Failed to fetch products", exception?.message)
    }
}

