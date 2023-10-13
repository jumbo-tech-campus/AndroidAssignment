package dev.sierov.jumbo

import android.app.Application
import dev.sierov.api.BaseUrl
import dev.sierov.shared.component.AndroidApplicationComponent
import dev.sierov.shared.component.create

class App : Application() {
    val component by lazy {
        AndroidApplicationComponent.create(
            application = this,
            baseUrl = BaseUrl.Default,
        )
    }
}