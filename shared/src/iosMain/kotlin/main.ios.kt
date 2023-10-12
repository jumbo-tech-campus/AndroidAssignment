import dev.sierov.shared.component.IosActivityComponent
import platform.UIKit.UIViewController

fun MainViewController(
    activityComponent: IosActivityComponent,
): UIViewController = activityComponent.appUiViewController()