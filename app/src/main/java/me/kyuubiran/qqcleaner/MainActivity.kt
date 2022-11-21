package me.kyuubiran.qqcleaner

import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.uitls.navigationBarLightOldMode
import me.kyuubiran.qqcleaner.uitls.setNavigationBarTranslation
import me.kyuubiran.qqcleaner.uitls.setStatusBarTranslation
import me.kyuubiran.qqcleaner.uitls.statusBarLightOldMode


class MainActivity : FragmentActivity() {
    val mainViewModel :MainActivityStates by viewModels()
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
        // 加载对应的布局
        this.setContentView(R.layout.main_activity)
    }

    public class MainActivityStates : ViewModel() {
        // 加载主题
        val theme = MutableStateFlow(LightColorPalette)
    }
}