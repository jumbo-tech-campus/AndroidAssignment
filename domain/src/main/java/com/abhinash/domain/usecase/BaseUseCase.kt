package com.abhinash.domain.usecase

import com.abhinash.domain.functional.Either
import com.abhinash.domain.functional.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface BaseUseCase<out Type, in Params> where Type : Any {

    suspend fun execute(params: Params): Either<Failure, Type>

    operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        val backgroundJob = scope.async { execute(params) }
        scope.launch { onResult(backgroundJob.await()) }
    }
}
