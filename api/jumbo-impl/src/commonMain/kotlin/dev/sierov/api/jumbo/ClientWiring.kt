package dev.sierov.api.jumbo

import dev.sierov.core.inject.ApplicationScoped
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import co.touchlab.kermit.Logger as KermitLogger

interface HttpClientWiring {

    @Provides
    @ApplicationScoped
    fun jsonFormat(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @ApplicationScoped
    fun httpClient(engine: HttpClientEngine, json: Json): HttpClient = HttpClient(engine) {
        // in order to read GitHub's raw 'endpoint' the content type cannot be application/json
        install(ContentNegotiation) { json(json = json, contentType = ContentType.Text.Any) }
        install(Logging) { logger = KtorLogger; level = LogLevel.INFO }
    }
}

private object KtorLogger : Logger {
    private val logger = KermitLogger.withTag("HTTP")
    override fun log(message: String) = logger.i(message)
}

expect interface HttpEngineWiring