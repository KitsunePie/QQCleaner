package me.kyuubiran.qqcleaner.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager

/**
 * 状态栏透明
 */
@Suppress("DEPRECATION")
fun Window.setStatusBarTranslation() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.isStatusBarContrastEnforced = false
    }
    // 设置状态栏透明,暂时没有更好的办法解决透明问题
    this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    this.statusBarColor = Color.TRANSPARENT
}

/**
 * 亮色状态栏
 * @param enable 默认为亮色
 */
fun Activity.statusBarLightMode(enable: Boolean = true) {
    this.window.setStatusBarTranslation()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.setSystemBarsAppearance(
            if (enable) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }
    this.statusBarLightOldMode(enable)
}

/**
 * 亮色状态栏（对应旧版本）
 * @param enable 默认为亮色
 */
@Suppress("DEPRECATION")
fun Activity.statusBarLightOldMode(enable: Boolean = true) {
    window.decorView.systemUiVisibility = if (enable) {
        window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        window.decorView.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

/**
 * 导航栏透明
 */
@Suppress("DEPRECATION")
fun Window.setNavigationBarTranslation() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        this.isNavigationBarContrastEnforced = false
    }
    // 设置导航栏透明,暂时没有更好的办法解决透明问题
    this.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    this.navigationBarColor = Color.TRANSPARENT
}

/**
 * 亮色导航栏
 * @param enable 默认为亮色
 */
@Suppress("DEPRECATION")
fun Activity.navigationBarMode(enable: Boolean = true) {
    this.window.setNavigationBarTranslation()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.setSystemBarsAppearance(
            if (enable) WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS else 0,
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
        )
    }
    this.navigationBarOldMode(enable)
}

/**
 * 亮色导航栏（对应旧版本）
 * @param enable 默认为亮色
 */
@Suppress("DEPRECATION")
fun Activity.navigationBarOldMode(enable: Boolean = true) {
    window.decorView.systemUiVisibility = if (enable) {
        window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    } else {
        window.decorView.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}


