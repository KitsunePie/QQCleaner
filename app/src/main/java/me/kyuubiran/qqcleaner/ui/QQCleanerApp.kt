package me.kyuubiran.qqcleaner.ui


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.About
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.Config
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.Developer
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.Main
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.Sort
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


        composable(Config) {
            ConfigScreen(navController = navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.route == Config) {

                }
            }
        }
        composable(Sort) {
            SortScreen(navController = navController)
        }
//        composable(SortFix) {
//            SortFixScreen(navController = navController)
//        }


        composable(About) {
            AboutScreen(navController = navController)
        }
        composable(Developer) {
            DeveloperScreen(navController = navController)
        }

    }

}

object QQCleanerApp {
    const val Config = "config_screen"
    const val Main = "main_screen"
    const val Developer = "developer_screen"
    const val Sort = "sort_screen"
    const val ConfigFix = "config_fix_screen"
    const val About = "about_screen"
    const val SortFix = "sort_fix_screen"
}
