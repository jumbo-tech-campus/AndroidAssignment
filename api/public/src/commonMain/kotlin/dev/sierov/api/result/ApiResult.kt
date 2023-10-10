package dev.sierov.api.result

import dev.sierov.api.ApiCallException
import dev.sierov.api.cause

/**
 * Represents a result from a traditional HTTP API. [ApiResult] has two sealed subtypes: [Success]
 * and [Failure]. [Success] is typed to [T] with no error type and [Failure] is typed to [E] with
 * no success type.
 *
 * [Failure] in turn is represented by four sealed subtypes of its own: [Failure.NetworkFailure],
 * [Failure.ApiFailure], [Failure.HttpFailure], and [Failure.UnknownFailure]. This allows for
 * simple handling of results through a consistent, non-exceptional flow via sealed `when` branches.
 *
 * ```
 * when (val result = myApi.someEndpoint()) {
 *   is Success -> doSomethingWith(result.response)
 *   is Failure -> when (result) {
 *     is NetworkFailure -> showError(result.error)
 *     is HttpFailure -> showError(result.code)
 *     is ApiFailure -> showError(result.error)
 *     is UnknownError -> showError(result.error)
 *   }
 * }
 * ```
 *
 * Usually, user code for this could just simply show a generic error message for a [Failure]
 * case, but a sealed class is exposed for more specific error messaging.
 */
public sealed class ApiResult<out T, out E> {

    /** A successful result with the data available in [response]. */
    public data class Success<T : Any>(public val response: T) : ApiResult<T, Nothing>()

    /** Represents a failure of some sort. */
    public sealed class Failure<out E> : ApiResult<Nothing, E>() {

        /**
         * Groups failures that were caused by a thrown exception
         * and have a corresponding [error] instance of it
         */
        sealed class ThrownFailure : Failure<Nothing>() {
            abstract val error: Throwable
        }

        /**
         * A base failure which descendants generally represent unrecoverable errors
         */
        public sealed class TerminalFailure : ThrownFailure()

        /**
         * An HTTP failure. This indicates a 4xx or 5xx response. The [code] is available for reference.
         *
         * @property code The HTTP status code.
         * @property error An optional [error][E]. This would be from the error body of the response.
         */
        public data class HttpFailure<out E> internal constructor(
            public val code: Int,
            public val error: E?,
        ) : Failure<E>()

        /**
         * A network failure caused by a given [error]. This error is opaque, as the actual type could
         * be from a number of sources (connectivity, etc). This event is generally considered to be a
         * non-recoverable and should be used as signal or logging before attempting to gracefully
         * degrade or retry.
         */
        public data class NetworkFailure internal constructor(
            public override val error: IoException,
        ) : ThrownFailure()

        /**
         * An API failure. This indicates a 2xx response where an exception was thrown
         * during response body conversion.
         */
        public data class ApiFailure internal constructor(
            public override val error: Throwable,
        ) : TerminalFailure()

        /**
         * An unknown failure caused by a given [error]. This error is opaque, as the actual type could
         * be from a number of sources (serialization issues, etc). This event is generally considered
         * to be a non-recoverable and should be used as signal or logging before attempting to
         * gracefully degrade or retry.
         */
        public data class UnknownFailure internal constructor(
            public override val error: Throwable,
        ) : TerminalFailure()
    }

    @Suppress("unused")
    public companion object {
        private const val OK = 200
        private val HTTP_SUCCESS_RANGE = OK..299
        private val HTTP_FAILURE_RANGE = 400..599

        /** Returns a new [HttpFailure] with given [code] and optional [error]. */
        public fun <E> httpFailure(code: Int, error: E? = null): Failure.HttpFailure<E> {
            checkHttpFailureCode(code)
            return Failure.HttpFailure(code, error)
        }

        /** Returns a new [ApiFailure] with given [error]. */
        public fun apiFailure(error: Throwable): Failure.ApiFailure = Failure.ApiFailure(error)

        /** Returns a new [NetworkFailure] with given [error]. */
        public fun networkFailure(error: IoException): Failure.NetworkFailure =
            Failure.NetworkFailure(error)

        /** Returns a new [UnknownFailure] with given [error]. */
        public fun unknownFailure(error: Throwable): Failure.UnknownFailure =
            Failure.UnknownFailure(error)

        private fun checkHttpFailureCode(code: Int) {
            require(code !in HTTP_SUCCESS_RANGE) {
                "Status code '$code' is a successful HTTP response. If you mean to use a $OK code + error " +
                        "string to indicate an API error, use the ApiResult.apiFailure() factory."
            }
            require(code in HTTP_FAILURE_RANGE) {
                "Status code '$code' is not a HTTP failure response. Must be a 4xx or 5xx code."
            }
        }
    }
}

typealias IoException = Exception

@Throws(ApiCallException::class)
fun <T> ApiResult<T, *>.getOrThrow(): T = when (this) {
    is ApiResult.Success -> response
    is ApiResult.Failure -> throw cause
}

inline fun <T, R : Any, E> ApiResult<T, E>.map(mapper: (T) -> R): ApiResult<R, E> = when (this) {
    is ApiResult.Success -> ApiResult.Success(mapper(response))
    is ApiResult.Failure -> this
}