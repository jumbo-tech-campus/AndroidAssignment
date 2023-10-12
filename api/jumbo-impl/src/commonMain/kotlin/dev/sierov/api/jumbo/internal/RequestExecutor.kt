@file:Suppress("unused")

package dev.sierov.api.jumbo.internal

import dev.sierov.api.result.ApiResult
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import me.tatarka.inject.annotations.Inject
import kotlin.random.Random
import kotlin.reflect.KType
import kotlin.reflect.typeOf

typealias RequestCoroutineDispatcher = CoroutineDispatcher

@Inject
class RequestExecutor(
    stringFormat: Json,
    private val config: Config = Config(strict = true),
    coroutineDispatcher: RequestCoroutineDispatcher = Dispatchers.Default,
) {

    data class Config(
        val strict: Boolean,
    )

    @PublishedApi
    internal val requestContext = RequestContext(
        coroutineDispatcher = coroutineDispatcher,
        stringFormat = stringFormat,
    )

    suspend inline fun <reified T : Any, reified E> execute(
        crossinline block: suspend RequestContext.() -> HttpResponse,
    ): ApiResult<T, E> = runCatching { block(requestContext) }.fold(
        onSuccess = { transformReceivedResponse(it) },
        onFailure = { transformFailedRequest(it) },
    )

    @PublishedApi
    internal suspend inline fun <reified T : Any, reified E> transformReceivedResponse(
        response: HttpResponse,
    ): ApiResult<T, E> = transformReceivedResponse(
        response = response,
        successType = typeInfo<T>(),
        failureType = typeInfo<E>(),
    )

    @Suppress("UNCHECKED_CAST")
    @PublishedApi
    internal suspend fun <T : Any, E> transformReceivedResponse(
        response: HttpResponse,
        successType: TypeInfo,
        failureType: TypeInfo,
    ): ApiResult<T, E> = if (response.status.isSuccess()) {
        if (successType == typeInfo<Unit>()) {
            ApiResult.Success(response = Unit as T)
        } else try {
            ApiResult.Success(response = response.body(successType))
        } catch (e: Throwable) {
            ApiResult.apiFailure(error = e)
        }
    } else {
        if (failureType == typeInfo<Unit>()) {
            ApiResult.httpFailure(code = response.status.value, error = Unit as E)
        } else try {
            ApiResult.httpFailure(code = response.status.value, error = response.body(failureType))
        } catch (e: Throwable) {
            ApiResult.httpFailure(code = response.status.value, error = null)
        }
    }

    @PublishedApi
    internal fun <T, E> transformFailedRequest(exception: Throwable): ApiResult<T, E> {
        if (exception is IllegalArgumentException) throw exception
        if (exception is NoTransformationFoundException) throw exception
        if (exception is SerializationException) throw exception
        if (config.strict && exception is IllegalStateException) throw exception
        if (exception is IOException) return ApiResult.networkFailure(exception)
        return ApiResult.unknownFailure(exception)
    }

    companion object {
        const val CacheMarker = "cacheMarker"
    }
}

class RequestContext internal constructor(
    private val stringFormat: StringFormat,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    suspend inline fun <reified T> T?.stringify(): String = stringify(typeOf<T>()) { throw it }

    suspend fun <T> T?.stringify(type: KType, fallback: (Throwable) -> String): String =
        with(stringFormat) {
            val serializer = serializersModule
                .runCatching { serializer(type) }
                .getOrElse { return fallback(it) }
            withContext(coroutineDispatcher) {
                encodeToString(serializer, value = this@stringify)
                    .removeSurrounding("\"")
            }
        }

    inline var HttpRequestBuilder.jsonBody: Any
        set(value) = setJsonBodyAndContentType(value)
        get() = body

    fun HttpRequestBuilder.setJsonBodyAndContentType(body: Any) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }

    /**
     * Hacky way to enforce cache refresh, see [disclaimer][dev.sierov.api.jumbo.ClientDrivenCacheControl]
     */
    fun HttpRequestBuilder.allowCache(allow: Boolean) {
        if (!allow) parameter(RequestExecutor.CacheMarker, Random.nextInt())
    }
}