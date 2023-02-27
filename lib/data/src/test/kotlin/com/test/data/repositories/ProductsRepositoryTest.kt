package com.test.data.repositories

import com.test.data.repository.ProductsRepository
import com.test.data.returnMockedProductsResponse
import com.test.data.source.ProductsRemoteDataSource
import com.test.network.backend.model.NetworkResponse
import com.test.network.backend.model.mapper.NetworkResult
import com.test.network.backend.model.mapper.toProductModel
import com.test.network.backend.service.ProductsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class ProductsRepositoryTest {

    private val productsApi: ProductsApi = mock()
    private val productsRemoteDataSource = ProductsRemoteDataSource(productsApi)
    private val productsRepository = ProductsRepository(productsRemoteDataSource)
    private val mockedCodeError = 400

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchProductListSuccessfully() {
        runTest {
            whenever(productsRemoteDataSource.fetchProductList())
                .thenReturn(NetworkResponse.Success(returnMockedProductsResponse()))
            whenever(productsApi.fetchProductList())
                .thenReturn(NetworkResponse.Success(returnMockedProductsResponse()))

            val response = productsRepository.fetchProductList()
            assert(response is NetworkResult.Success)
            val successResponse = response as NetworkResult.Success
            assertEquals(successResponse.result?.products?.size, 1)
        }
    }

    @Test
    fun fetchProductListWithApiError() {
        runTest {
            whenever(productsRemoteDataSource.fetchProductList())
                .thenReturn(
                    NetworkResponse.ApiError(
                        returnMockedProductsResponse()
                            .products.first().toProductModel(), mockedCodeError
                    )
                )
            whenever(productsApi.fetchProductList())
                .thenReturn(
                    NetworkResponse.ApiError(
                        returnMockedProductsResponse()
                            .products.first().toProductModel(), mockedCodeError
                    )
                )

            val response = productsRepository.fetchProductList()
            assertNotNull(response)
            assert(response is NetworkResult.Fail<*>)
        }
    }

    @Test
    fun fetchProductListNetworkError() {
        runTest {
            whenever(productsRemoteDataSource.fetchProductList())
                .thenReturn(NetworkResponse.NetworkError(IOException()))
            whenever(productsApi.fetchProductList())
                .thenReturn(NetworkResponse.NetworkError(IOException()))
            val response = productsRepository.fetchProductList()
            assertNotNull(response)
            assert(response is NetworkResult.Exception)
        }
    }
}
