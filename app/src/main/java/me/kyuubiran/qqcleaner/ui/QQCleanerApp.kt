package me.kyuubiran.qqcleaner.ui


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.ConfigFix
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.ConfigSpecify
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.Developer
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.Edit
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.Main
import me.kyuubiran.qqcleaner.ui.scene.*

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
        startDestination = Main
    ) {
        composable(Main) {
            MainScene(navController = navController)
        }

        composable(Developer) {
            DeveloperScene(navController = navController)
        }

        composable(ConfigSpecify) {
            ConfigSpecifyScreen(navController = navController)
        }

        composable(Edit) {
            EditScene(navController = navController)
        }

        composable(ConfigFix) {
            FixConfigScreen(navController = navController)
        }
    }

}

object QQCleanerApp {
    const val Edit = "editScreen"
    const val Main = "mainScreen"
    const val Developer = "developerScreen"
    const val ConfigSpecify = "configSpecifyScreen"
    const val ConfigFix = "fixConfigScreenScreen"
}
