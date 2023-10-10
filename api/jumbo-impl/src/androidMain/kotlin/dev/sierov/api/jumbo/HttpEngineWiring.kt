package dev.sierov.api.jumbo

import android.app.Application
import dev.sierov.core.inject.ApplicationScoped
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import me.tatarka.inject.annotations.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

@OptIn(ExperimentalMultiplatform::class)
@AllowDifferentMembersInActual
actual interface HttpEngineWiring {

    @Provides
    @ApplicationScoped
    fun httpEngine(application: Application): HttpClientEngine {
        val okhttp = OkHttpClient.Builder()
            .cache(application.createCache())
            .build()
        val okHttpConfig = OkHttpConfig().apply {
            preconfigured = okhttp
            addNetworkInterceptor(ClientDrivenCacheControl(maxAge = 1.days))
            addInterceptor(ForceConnectionlessCache(application))
        }
        return OkHttpEngine(config = okHttpConfig)
    }
}

private fun Application.createCache(): Cache =
    Cache(File(cacheDir, "network"), 50L * 1024 * 1024)