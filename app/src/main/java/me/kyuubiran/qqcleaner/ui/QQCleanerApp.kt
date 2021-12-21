package me.kyuubiran.qqcleaner.ui


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.kyuubiran.qqcleaner.QQCleanerViewModel
import me.kyuubiran.qqcleaner.ui.scene.ConfigSpecifyScreen
import me.kyuubiran.qqcleaner.ui.scene.DeveloperScene
import me.kyuubiran.qqcleaner.ui.scene.EditScene
import me.kyuubiran.qqcleaner.ui.scene.MainScene

/**
 * QQCleaner App 的 UI 唯一入口点
 *
 * @author Agoines
 */

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QQCleanerApp(viewModel: QQCleanerViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = QQCleanerApp.Main
    ) {
        composable(QQCleanerApp.Main) {
            MainScene(viewModel = viewModel, navController = navController)
        }

        composable(QQCleanerApp.Developer) {
            DeveloperScene(viewModel = viewModel, navController = navController)
        }

        composable(QQCleanerApp.ConfigSpecify) {
            ConfigSpecifyScreen(viewModel = viewModel, navController = navController)
        }

        composable(QQCleanerApp.Edit) {
            EditScene(navController = navController, viewModel = viewModel)
        }


    }

}

object QQCleanerApp {
    const val Edit = "edit_screen"
    const val Main = "main_screen"
    const val Developer = "developer_screen"
    const val ConfigSpecify = "configSpecify_screen"
}
