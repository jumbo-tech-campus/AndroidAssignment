package dev.sierov.api.jumbo

import dev.sierov.core.inject.ApplicationScoped
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import me.tatarka.inject.annotations.Provides

@OptIn(ExperimentalMultiplatform::class)
@AllowDifferentMembersInActual
actual interface HttpEngineWiring {

    @Provides
    @ApplicationScoped
    fun httpEngine(): HttpClientEngine = OkHttpEngine(config = OkHttpConfig())
}