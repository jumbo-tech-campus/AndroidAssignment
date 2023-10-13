package dev.sierov.shared.component

import dev.sierov.core.inject.ActivityScoped
import dev.sierov.shared.controller.AppUiViewController
import me.tatarka.inject.annotations.Component

@ActivityScoped
@Component
abstract class IosActivityComponent(
    @Component val applicationComponent: IosApplicationComponent,
) : FeaturesComponent {
    abstract val appUiViewController: AppUiViewController

    companion object
}