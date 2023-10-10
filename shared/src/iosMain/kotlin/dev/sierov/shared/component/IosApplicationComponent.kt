package dev.sierov.shared.component

import dev.sierov.api.jumbo.JumboApiComponent
import dev.sierov.core.inject.ApplicationScoped
import me.tatarka.inject.annotations.Component

@ApplicationScoped
@Component
abstract class IosApplicationComponent : JumboApiComponent {
    companion object
}