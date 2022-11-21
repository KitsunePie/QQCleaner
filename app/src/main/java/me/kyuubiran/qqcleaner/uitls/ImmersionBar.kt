package me.kyuubiran.qqcleaner.uitls

import android.app.Activity
import android.graphics.Color.TRANSPARENT
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.*
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager.LayoutParams.*

/**
 * 在 Activity 的上下文中，设置状态栏透明
 */
context(Activity)
@Suppress("DEPRECATION")
fun setStatusBarTranslation() {
    window.apply {
        if (SDK_INT >= Q)
            isStatusBarContrastEnforced = false
        // 设置状态栏透明,暂时没有更好的办法解决透明问题
        addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        clearFlags(FLAG_TRANSLUCENT_STATUS)
        statusBarColor = TRANSPARENT
    }
}


/**
 * 在 Activity 的上下文中，设置导航栏透明
 */
context(Activity)
@Suppress("DEPRECATION")
fun setNavigationBarTranslation() {
    // 设置导航栏透明,暂时没有更好的办法解决透明问题
    window.apply {
        addFlags(FLAG_TRANSLUCENT_NAVIGATION)
        // 防止透明以后高对比度
        if (SDK_INT >= Q)
            isNavigationBarContrastEnforced = false
        // 去除导航栏线段
        if (SDK_INT >= P)
            navigationBarDividerColor = TRANSPARENT
        navigationBarColor = TRANSPARENT
    }
}

/**
 * 在 Activity 的上下文中，设置亮色状态栏
 * @param enable 默认为亮色
 * https://qa.1r1g.com/sf/ask/4595541261/ 没有找到英文原文，
 * 这个更适合在页面有绘制的 View 的时候调用
 */
context(Activity)
fun statusBarLightMode(enable: Boolean = true) {
    if (SDK_INT >= R)
        window.insetsController?.apply {
            setSystemBarsAppearance(
                if (enable) APPEARANCE_LIGHT_STATUS_BARS else 0,
                APPEARANCE_LIGHT_STATUS_BARS
            )
        }
    statusBarLightOldMode(enable)
}

/**
 * 在 Activity 的上下文中，设置亮色状态栏（对应旧版本）
 * @param enable 默认为亮色
 */
context(Activity)
@Suppress("DEPRECATION")
fun statusBarLightOldMode(enable: Boolean = true) {
    window.decorView.apply {
        systemUiVisibility = if (enable)
            systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else
            systemUiVisibility xor SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}


/**
 * 在 Activity 的上下文中，设置亮色导航栏
 * @param enable 默认为亮色
 * 同上
 */
context(Activity)
fun navigationBarLightMode(enable: Boolean = true) {
    setNavigationBarTranslation()
    if (SDK_INT >= R)
        window.insetsController?.apply {
            setSystemBarsAppearance(
                if (enable) APPEARANCE_LIGHT_NAVIGATION_BARS else 0,
                APPEARANCE_LIGHT_NAVIGATION_BARS
            )
        }
    navigationBarLightOldMode(enable)
}

/**
 * 在 Activity 的上下文中，设置亮色导航栏（对应旧版本）
 * @param enable 默认为亮色
 */
context(Activity)
@Suppress("DEPRECATION")
fun navigationBarLightOldMode(enable: Boolean = true) {
    if (SDK_INT >= O)
        window.decorView.apply {
            systemUiVisibility = if (enable)
                systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            else
                systemUiVisibility xor SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
}