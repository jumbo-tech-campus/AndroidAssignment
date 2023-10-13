package dev.sierov.api.jumbo

import dev.sierov.api.BaseUrl
import dev.sierov.api.ProductApi
import dev.sierov.core.inject.ApplicationScoped
import me.tatarka.inject.annotations.Provides

interface JumboApiComponent : HttpClientWiring, HttpEngineWiring {

    val baseUrl: BaseUrl

    @Provides
    @ApplicationScoped
    fun productApi(api: JumboProductApi): ProductApi = api
}