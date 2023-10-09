import dev.sierov.shared.component.IosActivityComponent
import platform.UIKit.UIViewController

actual fun getPlatformName(): String = "iOS"

fun MainViewController(
    activityComponent: IosActivityComponent,
): UIViewController = activityComponent.appUiViewController()