package dev.sierov.jumbo

import android.app.Application
import android.os.StrictMode
import dev.sierov.api.BaseUrl
import dev.sierov.shared.component.AndroidApplicationComponent
import dev.sierov.shared.component.create
import okhttp3.mockwebserver.MockWebServer

class App : Application() {

    val server by lazy {
        // we want to spin up our mock server for tests and
        // immediately revert to strict network policy
        withUnrestrictedNetwork(::MockWebServer)
    }

    val component by lazy {
        AndroidApplicationComponent.create(
            application = this,
            baseUrl = BaseUrl(url = server.url("/server").toString()),
        )
    }

    private inline fun <T> withUnrestrictedNetwork(block: () -> T): T {
        val networkRelaxed = StrictMode.ThreadPolicy.Builder()
            .permitNetwork()
            .build()
        val networkRestricted = StrictMode.ThreadPolicy.Builder()
            .penaltyDeathOnNetwork()
            .build()
        StrictMode.setThreadPolicy(networkRelaxed)
        val result = block()
        StrictMode.setThreadPolicy(networkRestricted)
        return result
    }
}