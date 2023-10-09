import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let applicationComponent = IosApplicationComponent.companion.create()
        let activityComponent = IosActivityComponent.companion.create(
            applicationComponent: applicationComponent
        )
        return Main_iosKt.MainViewController(activityComponent: activityComponent)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.all, edges: .bottom) // Compose has own keyboard handler
    }
}



