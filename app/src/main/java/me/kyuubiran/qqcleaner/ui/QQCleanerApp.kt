package me.kyuubiran.qqcleaner.ui


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.About
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
        QQCleanerData.isFirst = false
        composable(Main) {
            MainScreen(navController = navController)
        }

        composable(Developer) {
            DeveloperScreen(navController = navController)
        }

        composable(ConfigSpecify) {
            ConfigSpecifyScreen(navController = navController)
        }

        composable(Edit) {
            EditScreen(navController = navController)
        }

        composable(ConfigFix) {
            FixConfigScreen(navController = navController)
        }

        composable(About) {
            AboutScreen(navController = navController)
        }
    }

}

object QQCleanerApp {
    const val Edit = "editScreen"
    const val Main = "mainScreen"
    const val Developer = "developerScreen"
    const val ConfigSpecify = "configSpecifyScreen"
    const val ConfigFix = "fixConfigScreenScreen"
    const val About = "aboutScreen"
}
