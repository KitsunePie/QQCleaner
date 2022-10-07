package me.kyuubiran.qqcleaner

import android.os.Bundle
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import me.kyuubiran.qqcleaner.uitls.navigationBarLightOldMode
import me.kyuubiran.qqcleaner.uitls.setNavigationBarTranslation
import me.kyuubiran.qqcleaner.uitls.setStatusBarTranslation
import me.kyuubiran.qqcleaner.uitls.statusBarLightOldMode

class MainActivity : FragmentActivity() {
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
        this.setContentView(R.layout.main_activity)
    }

}