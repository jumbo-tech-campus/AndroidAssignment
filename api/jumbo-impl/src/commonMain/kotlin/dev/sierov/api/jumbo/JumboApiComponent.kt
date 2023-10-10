package dev.sierov.api.jumbo

import dev.sierov.api.ProductApi
import dev.sierov.core.inject.ApplicationScoped
import me.tatarka.inject.annotations.Provides

interface JumboApiComponent : HttpClientWiring, HttpEngineWiring {

    @Provides
    @ApplicationScoped
    fun productApi(api: JumboProductApi): ProductApi = api
}