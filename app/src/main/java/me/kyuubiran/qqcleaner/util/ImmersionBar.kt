package me.kyuubiran.qqcleaner.util

import android.app.Activity
import android.graphics.Color.TRANSPARENT
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.Q
import android.os.Build.VERSION_CODES.R
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.Window
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager.LayoutParams.*

/**
 * 状态栏透明
 */
@Suppress("DEPRECATION")
fun Window.setStatusBarTranslation() {
    if (SDK_INT >= Q)
        this.isStatusBarContrastEnforced = false
    // 设置状态栏透明,暂时没有更好的办法解决透明问题
    this.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.clearFlags(FLAG_TRANSLUCENT_STATUS)
    this.statusBarColor = TRANSPARENT
}

/**
 * 亮色状态栏
 * @param enable 默认为亮色
 */
fun Activity.statusBarLightMode(enable: Boolean = true) {
    this.window.setStatusBarTranslation()
    if (SDK_INT >= R)
        window.insetsController?.setSystemBarsAppearance(
            if (enable) APPEARANCE_LIGHT_STATUS_BARS else 0,
            APPEARANCE_LIGHT_STATUS_BARS
        )
    this.statusBarLightOldMode(enable)
}

/**
 * 亮色状态栏（对应旧版本）
 * @param enable 默认为亮色
 */
@Suppress("DEPRECATION")
fun Activity.statusBarLightOldMode(enable: Boolean = true) {
    window.decorView.systemUiVisibility = if (enable)
        window.decorView.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    else
        window.decorView.systemUiVisibility xor SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

/**
 * 导航栏透明
 */
@Suppress("DEPRECATION")
fun Window.setNavigationBarTranslation() {
    // 防止透明以后高对比度
    if (SDK_INT >= Q)
        this.isNavigationBarContrastEnforced = false
    // 去除导航栏线段
    if (SDK_INT >= Build.VERSION_CODES.P)
        this.navigationBarDividerColor = TRANSPARENT
    // 设置导航栏透明,暂时没有更好的办法解决透明问题
    this.addFlags(FLAG_TRANSLUCENT_NAVIGATION)
    this.navigationBarColor = TRANSPARENT
}

/**
 * 亮色导航栏
 * @param enable 默认为亮色
 */
@Suppress("DEPRECATION")
fun Activity.navigationBarMode(enable: Boolean = true) {
    this.window.setNavigationBarTranslation()
    if (SDK_INT >= R)
        window.insetsController?.setSystemBarsAppearance(
            if (enable) APPEARANCE_LIGHT_NAVIGATION_BARS else 0,
            APPEARANCE_LIGHT_NAVIGATION_BARS
        )
    this.navigationBarOldMode(enable)
}

/**
 * 亮色导航栏（对应旧版本）
 * @param enable 默认为亮色
 */
@Suppress("DEPRECATION")
fun Activity.navigationBarOldMode(enable: Boolean = true) {
    window.decorView.systemUiVisibility = if (enable)
        window.decorView.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    else
        window.decorView.systemUiVisibility xor SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

}


