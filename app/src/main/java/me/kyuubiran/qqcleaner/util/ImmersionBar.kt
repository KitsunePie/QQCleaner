package me.kyuubiran.qqcleaner.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager


@Suppress("DEPRECATION")
private fun Window.setStatusBarTranslation() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.isStatusBarContrastEnforced = false
    }
    // 设置状态栏透明,暂时没有更好的办法解决透明问题
    this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    this.statusBarColor = Color.TRANSPARENT
}

@Suppress("DEPRECATION")
fun Activity.statusBarLightMode(enable: Boolean = true) {
    this.window.setStatusBarTranslation()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.setSystemBarsAppearance(
            if (enable) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }
    window.decorView.systemUiVisibility = if (enable) {
        window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        window.decorView.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}

@Suppress("DEPRECATION")
private fun Window.setNavigationBarTranslation() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        this.isNavigationBarContrastEnforced = false
    }
    // 设置导航栏透明,暂时没有更好的办法解决透明问题
    this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    this.navigationBarColor = Color.TRANSPARENT
}

@Suppress("DEPRECATION")
fun Activity.navigationBarMode(enable: Boolean = true) {
    this.window.setNavigationBarTranslation()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.setSystemBarsAppearance(
            if (enable) WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS else 0,
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
        )
    }
    window.decorView.systemUiVisibility = if (enable) {
        window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    } else {
        window.decorView.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}


