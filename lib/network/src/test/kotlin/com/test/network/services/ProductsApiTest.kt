package com.test.network.services

import com.test.network.BuildConfig
import com.test.network.backend.adapter.NetworkResponseAdapterFactory
import com.test.network.backend.model.NetworkResponse
import com.test.network.backend.service.ProductsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val FETCH_PRODUCTS_STATUS_200 = "mock-responses/get-product-list-status200.json"
const val FETCH_PRODUCTS_STATUS_400 = "mock-responses/get-product-list-status400.json"

@ExperimentalCoroutinesApi
class ProductsApiTest {

    private lateinit var productsApi: ProductsApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        productsApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.API_BASE_URL))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ProductsApi::class.java)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain()
    }

    @Test
    fun responseFetchProductListSuccessfully() {
        enqueueResponse(FETCH_PRODUCTS_STATUS_200)
        runTest {
            val response =
                productsApi.fetchProductList()
            assert(
                response is
                NetworkResponse.Success
            )
        }
    }

    @Test
    fun responseFetchProductListWithError() {
        enqueueResponse(FETCH_PRODUCTS_STATUS_400)
        runTest {
            val response =
                productsApi.fetchProductList()
            assertEquals(400, response)
            assertNull(response)
        }
    }

    private fun enqueueResponse(filePath: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
        val bufferSource = inputStream?.source()?.buffer()
        val mockResponse = MockResponse()

        mockWebServer.enqueue(
            mockResponse.setBody(
                bufferSource!!.readString(Charsets.UTF_8)
            )
        )
    }
}
