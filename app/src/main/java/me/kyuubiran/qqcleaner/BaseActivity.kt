package me.kyuubiran.qqcleaner

import android.content.res.Configuration
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import me.kyuubiran.qqcleaner.QQCleanerData.isDark
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.*
import me.kyuubiran.qqcleaner.util.*
//open class BaseActivity : ComponentActivity()
open class BaseActivity : TestActivity() {

    private val mLoder by lazy { BaseActivity::class.java.classLoader }

    override fun getClassLoader(): ClassLoader = mLoder

    override fun onCreate(savedInstanceState: Bundle?) {
        // 状态栏和导航栏延伸
        this.requestWindowFeature(FEATURE_NO_TITLE)
        setDecorFitsSystemWindows(window, false)
        // 输入法抬升
        @Suppress("DEPRECATION")
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        // 这个是保证第一次打开的时候状态栏和导航栏变色
        // 用旧版的原因很简单， window.insetsController 在这个时候获取不到，所以这个
        setBarTranslation()
        isDark = when (QQCleanerData.theme) {
            Light -> false
            Dark -> true
            System -> isNightMode()
        }
        setLightOldMode(!isDark)
        super.onCreate(savedInstanceState)


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val windowState = savedInstanceState.getBundle("android:viewHierarchyState")
        if (windowState != null) {
            windowState.classLoader = mLoder
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    /**
     * 设置导航栏和状态栏透明
     */
    private fun setBarTranslation() {
        window.setStatusBarTranslation()
        window.setNavigationBarTranslation()
    }

    /**
     * 设置亮色导航栏和状态栏
     * @param enable 是否设置亮色导航栏和状态栏
     */
    fun setLightMode(enable: Boolean = true) {
        statusBarLightMode(enable)
        navigationBarMode(enable)
    }

    /**
     * 设置亮色导航栏和状态栏（旧版本）
     * @param enable 是否设置亮色导航栏和状态栏
     */
    private fun setLightOldMode(enable: Boolean = true) {
        statusBarLightOldMode(enable)
        navigationBarOldMode(enable)
    }

    /**
     * 判断当前是否是暗色模式
     * @return 当前是否是暗色模式
     */
    private fun isNightMode(): Boolean {
        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }
}