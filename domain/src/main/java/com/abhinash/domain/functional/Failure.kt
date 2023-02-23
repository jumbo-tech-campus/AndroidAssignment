package com.abhinash.domain.functional

/**
 * Base Class for handling errors/failures/exceptions. Every feature specific failure should extend
 * [FeatureFailure] class.
 */
sealed class Failure(val exception: Throwable = Throwable("Failure")) {

    /** * Extend this class for feature specific failures. */
    open class FeatureFailure(val featureException: Throwable = Throwable("Feature failure")) :
        Failure(featureException)
}
