package com.test.data.usecases

import com.test.data.repository.ProductsRepository
import com.test.data.returnMockedProductsResponse
import com.test.data.source.ProductsRemoteDataSource
import com.test.data.usecase.GetProductListUseCase
import com.test.network.backend.model.NetworkResponse
import com.test.network.backend.model.mapper.NetworkResult
import com.test.network.backend.model.mapper.toProductModel
import com.test.network.backend.service.ProductsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class GetProductListUseCaseTest {

    private val productsApi: ProductsApi = mock()
    private val productsRemoteDataSource = ProductsRemoteDataSource(productsApi)
    private val productsRepository = ProductsRepository(productsRemoteDataSource)
    private val getProductListUseCase = GetProductListUseCase(productsRepository)
    private val mockedCodeError = 400

    @Test
    fun fetchProductListSuccessfully() {
        runTest {
            whenever(productsApi.fetchProductList())
                .thenReturn(NetworkResponse.Success(returnMockedProductsResponse()))
            whenever(productsRepository.fetchProductList())
                .thenReturn(NetworkResult.Success(returnMockedProductsResponse().toProductModel()))
            whenever(productsRemoteDataSource.fetchProductList())
                .thenReturn(NetworkResponse.Success(returnMockedProductsResponse()))

            val response = getProductListUseCase.fetchProductListInfo()
            assert(response is NetworkResult.Success)
            val successResponse = response as NetworkResult.Success
            Assert.assertEquals(successResponse.result?.products?.size, 1)
        }
    }

    @Test
    fun fetchProductListWithApiError() {
        runTest {
            whenever(productsApi.fetchProductList())
                .thenReturn(
                    NetworkResponse.ApiError(
                        returnMockedProductsResponse().products.first().toProductModel(),
                        mockedCodeError
                    )
                )
            whenever(productsRepository.fetchProductList())
                .thenReturn(NetworkResult.Fail(returnMockedProductsResponse()))
            whenever(productsRemoteDataSource.fetchProductList())
                .thenReturn(
                    NetworkResponse.ApiError(
                        returnMockedProductsResponse().products.first().toProductModel(),
                        mockedCodeError
                    )
                )

            val response = productsRepository.fetchProductList()
            Assert.assertNotNull(response)
            assert(response is NetworkResult.Fail<*>)
        }
    }

    @Test
    fun fetchProductListNetworkError() {
        runTest {
            whenever(productsApi.fetchProductList())
                .thenReturn(NetworkResponse.NetworkError(IOException()))
            whenever(productsRepository.fetchProductList())
                .thenReturn(NetworkResult.Exception(IOException()))
            whenever(productsRemoteDataSource.fetchProductList())
                .thenReturn(NetworkResponse.NetworkError(IOException()))
            val response = productsRepository.fetchProductList()
            Assert.assertNotNull(response)
            assert(response is NetworkResult.Exception)
        }
    }
}