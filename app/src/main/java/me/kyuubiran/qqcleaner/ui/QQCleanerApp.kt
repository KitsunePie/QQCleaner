package me.kyuubiran.qqcleaner.ui


import android.os.Build
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QQCleanerApp(viewModel: QQCleanerViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main_screen"
    ) {
        composable("main_screen") {
            MainScene(viewModel = viewModel)
        }

        composable("developer_screen") {
            DeveloperScene(navController = navController)
        }

        composable("configSpecify_screen") {
            ConfigSpecifyScreen(navController = navController)
        }

        composable("edit_screen") {
            EditScene(navController = navController, viewModel = viewModel)
        }


    }

}

