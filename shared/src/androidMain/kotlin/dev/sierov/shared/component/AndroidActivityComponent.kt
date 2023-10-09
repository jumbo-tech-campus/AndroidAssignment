package dev.sierov.shared.component

import android.app.Activity
import dev.sierov.core.inject.ActivityScoped
import dev.sierov.feature.root.AppContent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@ActivityScoped
@Component
abstract class AndroidActivityComponent(
    @get:Provides val activity: Activity,
    @Component val applicationComponent: AndroidApplicationComponent,
) : FeaturesComponent {
    abstract val appContent: AppContent

    companion object
}