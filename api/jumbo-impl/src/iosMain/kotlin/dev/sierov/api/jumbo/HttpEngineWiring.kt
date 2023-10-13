package dev.sierov.api.jumbo

import dev.sierov.core.inject.ApplicationScoped
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import me.tatarka.inject.annotations.Provides

@OptIn(ExperimentalMultiplatform::class)
@AllowDifferentMembersInActual
actual interface HttpEngineWiring {

    @Provides
    @ApplicationScoped
    fun httpEngine(): HttpClientEngine = Darwin.create { }
}