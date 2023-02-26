package com.test.network.backend.model.mapper

import com.test.network.backend.model.NetworkResponse

sealed class NetworkResult<out T : Any> {
    /**
     * Success
     */
    data class Success<T : Any>(val result: T?) : NetworkResult<T>()

    /**
     * Failure
     */
    data class Fail<U : Any>(val errorResult: U) : NetworkResult<Nothing>()

    /**
     * Network error
     */
    data class Exception(val error: Throwable?) : NetworkResult<Nothing>()
}

fun <T : Any> NetworkResponse<T, Any>.toRepositoryResult(): NetworkResult<T> {
    return when (this) {
        is NetworkResponse.Success -> NetworkResult.Success(this.body)
        is NetworkResponse.ApiError -> NetworkResult.Fail(this)
        is NetworkResponse.NetworkError -> NetworkResult.Exception(this.error)
        is NetworkResponse.UnknownError -> NetworkResult.Exception(this.error)
    }
}

fun <T : Any, U : Any> NetworkResponse<T, Any>.toRepositoryResult(mapper: (from: T) -> U): NetworkResult<U> {
    return when (this) {
        is NetworkResponse.Success -> NetworkResult.Success(
            if (this.body != null)
                mapper(this.body)
            else null
        )
        is NetworkResponse.ApiError -> NetworkResult.Fail(this)
        is NetworkResponse.NetworkError -> NetworkResult.Exception(this.error)
        is NetworkResponse.UnknownError -> NetworkResult.Exception(this.error)
    }
}
