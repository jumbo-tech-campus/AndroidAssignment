import dev.sierov.shared.component.IosActivityComponent
import platform.UIKit.UIViewController

@Suppress("unused", "FunctionName")
fun MainViewController(
    activityComponent: IosActivityComponent,
): UIViewController = activityComponent.appUiViewController()