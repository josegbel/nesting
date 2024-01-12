import SwiftUI
import shared

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)

        let mainViewController = PlatformKt.MainViewController(window: window!)
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
            print("to the background we go! From AppDelegate")

        }

     func application(
        _ application: UIApplication,
        supportedInterfaceOrientationsFor supportedInterfaceOrientationsForWindow: UIWindow?
     ) -> UIInterfaceOrientationMask {
         return UIInterfaceOrientationMask.all
    }
}
