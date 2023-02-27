package com.test.network.di

import com.test.network.BuildConfig
import com.test.network.NetworkModule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class NetworkModuleTest {

    private lateinit var networkModule: NetworkModule

    @Before
    fun setUp() {
        networkModule = NetworkModule
    }

    @Test
    fun verifyProvidedHttpClient() {
        val httpClient = networkModule.provideHttpClient()
        assertEquals(20000, httpClient.connectTimeoutMillis)
    }

    @Test
    fun verifyProvidedRetrofitBuilder() {
        val retrofit = networkModule.provideRetrofitBuilder(mock())
        assertEquals(
            BuildConfig.API_BASE_URL,
            retrofit.baseUrl().toString()
        )
    }
}
