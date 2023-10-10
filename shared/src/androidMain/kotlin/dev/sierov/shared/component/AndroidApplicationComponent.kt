package dev.sierov.shared.component

import android.app.Application
import dev.sierov.api.BaseUrl
import dev.sierov.api.jumbo.JumboApiComponent
import dev.sierov.core.inject.ApplicationScoped
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@ApplicationScoped
abstract class AndroidApplicationComponent(
    @get:Provides override val baseUrl: BaseUrl,
    @get:Provides val application: Application,
) : JumboApiComponent {
    companion object
}