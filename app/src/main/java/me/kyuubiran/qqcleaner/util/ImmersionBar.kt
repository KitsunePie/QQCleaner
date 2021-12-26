package me.kyuubiran.qqcleaner.util

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController

@Suppress("DEPRECATION")
fun Activity.statusBarLightMode(enable: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.setSystemBarsAppearance(
            if (enable) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else {
        window.decorView.systemUiVisibility = if (enable) {
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}

@Suppress("DEPRECATION")
fun Activity.navigationBarMode(enable: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.setSystemBarsAppearance(
            if (enable) WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS else 0,
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
        )
    } else {
        window.decorView.systemUiVisibility = if (enable) {
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            window.decorView.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
}


