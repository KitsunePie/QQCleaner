package me.kyuubiran.qqcleaner

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.uitls.dataStore
import me.kyuubiran.qqcleaner.uitls.navigationBarLightOldMode
import me.kyuubiran.qqcleaner.uitls.setNavigationBarTranslation
import me.kyuubiran.qqcleaner.uitls.setStatusBarTranslation
import me.kyuubiran.qqcleaner.uitls.statusBarLightOldMode


class MainActivity : FragmentActivity() {
    val mainViewModel: MainActivityStates by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        // 去除顶栏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // 导航栏与状态栏沉浸
        setStatusBarTranslation()
        setNavigationBarTranslation()
        statusBarLightOldMode()
        navigationBarLightOldMode()
        super.onCreate(savedInstanceState)
        // 关闭系统暗色模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            this.window.decorView.isForceDarkAllowed = false

        // 加载对应的布局
        this.setContentView(R.layout.main_activity)
        lifecycleScope.launch {
            dataStore.data.first()
        }

    }

    class MainActivityStates : ViewModel() {
        // 加载主题
        val theme = MutableStateFlow(LightColorPalette)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {

            }
            Configuration.UI_MODE_NIGHT_YES -> {

            }

        }
    }

}

