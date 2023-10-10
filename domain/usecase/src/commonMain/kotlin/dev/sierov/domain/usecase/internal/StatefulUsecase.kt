package dev.sierov.domain.usecase.internal

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

abstract class StatefulUsecase<P : Any, R> {

    private val count = atomic(initial = 0)
    private val currentlyInProgress = MutableStateFlow(count.value)

    val inProgress: Flow<Boolean> = currentlyInProgress
        .map { it > 0 }
        .distinctUntilChanged()

    protected abstract suspend fun doWork(params: P): R

    suspend operator fun invoke(
        params: P,
        timeout: Duration = DefaultTimeout,
    ): R = try {
        addInProgress()
        withTimeout(timeout) { doWork(params) }
    } finally {
        removeInProgress()
    }

    private fun addInProgress() {
        currentlyInProgress.value = count.incrementAndGet()
    }

    private fun removeInProgress() {
        currentlyInProgress.value = count.decrementAndGet()
    }

    companion object {
        internal val DefaultTimeout = 2.minutes
    }
}