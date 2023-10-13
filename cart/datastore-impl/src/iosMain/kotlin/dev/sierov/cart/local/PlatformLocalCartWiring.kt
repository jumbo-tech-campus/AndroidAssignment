package dev.sierov.cart.local

import dev.sierov.core.inject.ApplicationScoped
import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalMultiplatform::class)
@AllowDifferentMembersInActual
actual interface PlatformLocalCartWiring {

    @OptIn(ExperimentalForeignApi::class)
    @Provides
    @ApplicationScoped
    fun cartDatastoreDirectory(): CartDatastoreDirectory {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path) { "documentDirectory path is null" }
    }
}