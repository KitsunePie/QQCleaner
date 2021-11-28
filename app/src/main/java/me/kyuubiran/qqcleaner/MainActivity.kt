package me.kyuubiran.qqcleaner

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.activity.BaseActivity
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTheme


import me.kyuubiran.qqcleaner.ui.utils.navigationBarMode
import me.kyuubiran.qqcleaner.ui.utils.statusBarLightMode

class MainActivity : BaseActivity() {

    private val viewModel: QQCleanerViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDecorFitsSystemWindows(window, false)
        setContent {
            QQCleanerTheme(viewModel.theme) {
                QQCleanerApp()
            }
        }
        this.statusBarLightMode()

        this.navigationBarMode()
    }
}