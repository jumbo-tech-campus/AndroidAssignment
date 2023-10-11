package dev.sierov.cart.local

import android.app.Application
import dev.sierov.core.inject.ApplicationScoped
import me.tatarka.inject.annotations.Provides
import java.io.File

@OptIn(ExperimentalMultiplatform::class)
@AllowDifferentMembersInActual
actual interface PlatformLocalCartWiring {

    @Provides
    @ApplicationScoped
    fun cartDatastoreDirectory(application: Application): CartDatastoreDirectory =
        "${File(application.filesDir, "datastore")}"
}