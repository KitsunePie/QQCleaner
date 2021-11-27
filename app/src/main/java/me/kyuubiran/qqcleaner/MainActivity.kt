package me.kyuubiran.qqcleaner

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.lifecycle.Observer
import com.zackratos.ultimatebarx.ultimatebarx.navigationBar
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTheme

class MainActivity : AppCompatActivity() {

    private val viewModel: QQCleanerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDecorFitsSystemWindows(window, false)
        setContent {
            QQCleanerTheme(viewModel.theme) {
                QQCleanerApp()
            }
        }
        statusBar {
            transparent()
        }
        navigationBar {
            transparent()
        }
        val statusBarObserver = Observer<Boolean> { isLight ->
            statusBar {
                transparent()
                light = isLight
            }
        }

        viewModel.statusBarLight.observe(this, statusBarObserver)

        val navigationBarLight = Observer<Boolean> { isLight ->
            navigationBar {
                transparent()
                light = isLight
            }
        }

        viewModel.navigationBarLight.observe(this, navigationBarLight)

    }
}