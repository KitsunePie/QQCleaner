package me.kyuubiran.qqcleaner.ui


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kyuubiran.qqcleaner.ui.scene.ConfigSpecifyScreen
import me.kyuubiran.qqcleaner.ui.scene.DeveloperScene
import me.kyuubiran.qqcleaner.ui.scene.EditScene
import me.kyuubiran.qqcleaner.ui.scene.MainScene

/**
 * QQCleaner App 的 UI 唯一入口点
 *
 * @author Agoines
 */
@Composable
fun QQCleanerApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = QQCleanerApp.Main
    ) {
        composable(QQCleanerApp.Main) {
            MainScene(navController = navController)
        }

        composable(QQCleanerApp.Developer) {
            DeveloperScene(navController = navController)
        }

        composable(QQCleanerApp.ConfigSpecify) {
            ConfigSpecifyScreen(navController = navController)
        }

        composable(QQCleanerApp.Edit) {
            EditScene(navController = navController)
        }


    }

}

object QQCleanerApp {
    const val Edit = "edit_screen"
    const val Main = "main_screen"
    const val Developer = "developer_screen"
    const val ConfigSpecify = "configSpecify_screen"
}
