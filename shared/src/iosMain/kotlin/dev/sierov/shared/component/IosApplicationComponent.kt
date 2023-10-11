package dev.sierov.shared.component

import dev.sierov.api.BaseUrl
import dev.sierov.api.jumbo.JumboApiComponent
import dev.sierov.cart.local.LocalCartComponent
import dev.sierov.core.inject.ApplicationScoped
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ApplicationScoped
@Component
abstract class IosApplicationComponent(
    @get:Provides override val baseUrl: BaseUrl,
) : JumboApiComponent, LocalCartComponent {
    companion object
}