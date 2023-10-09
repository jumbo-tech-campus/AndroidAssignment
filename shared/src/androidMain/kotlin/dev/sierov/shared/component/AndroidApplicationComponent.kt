package dev.sierov.shared.component

import android.app.Application
import dev.sierov.core.inject.ApplicationScoped
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@ApplicationScoped
abstract class AndroidApplicationComponent(
    @get:Provides val application: Application,
) {
    companion object
}