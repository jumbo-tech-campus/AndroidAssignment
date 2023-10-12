@file:Suppress("unused")

package dev.sierov.api

import dev.sierov.api.result.ApiResult

class ApiCallException @PublishedApi internal constructor(
    val failure: ApiResult.Failure<*>,
) : RuntimeException(
    failure.toString(),
    when (failure) {
        is ApiResult.Failure.HttpFailure -> null
        is ApiResult.Failure.ThrownFailure -> failure.error
    }
)

inline val ApiResult.Failure<*>.cause: Throwable get() = ApiCallException(failure = this)
inline val ApiResult<*, *>.causeOrNull: Throwable? get() = if (this is ApiResult.Failure) cause else null
fun ApiResult.Failure<*>.httpCodeOrNull(): Int? = (this as? ApiResult.Failure.HttpFailure)?.code
fun ApiCallException.httpCodeOrNull(): Int? = failure.httpCodeOrNull()