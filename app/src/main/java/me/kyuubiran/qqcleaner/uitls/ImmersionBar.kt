package me.kyuubiran.qqcleaner.uitls

import android.app.Activity
import android.graphics.Color.TRANSPARENT
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.*
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.Window
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager.LayoutParams.*


context(Activity)
fun setStatusBarTranslation() {
    window.setStatusBarTranslation()
}

/**
 * 设置状态栏透明
 */
@Suppress("DEPRECATION")
fun Window.setStatusBarTranslation() {
    if (SDK_INT >= Q)
        isStatusBarContrastEnforced = false
    // 设置状态栏透明,暂时没有更好的办法解决透明问题
    clearFlags(FLAG_TRANSLUCENT_STATUS)
    addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    if (isMiuiDevices())
        addFlags(FLAG_TRANSLUCENT_STATUS)
    statusBarColor = TRANSPARENT
}


context(Activity)
fun setNavigationBarTranslation() {
    window.setNavigationBarTranslation()
}

/**
 * 设置导航栏透明
 */
@Suppress("DEPRECATION")
fun Window.setNavigationBarTranslation() {
    clearFlags(FLAG_TRANSLUCENT_NAVIGATION)
    if (isMiuiDevices())
        addFlags(FLAG_TRANSLUCENT_NAVIGATION)
    // 防止透明以后高对比度
    if (SDK_INT >= Q)
        isNavigationBarContrastEnforced = false
    // 去除导航栏线段
    if (SDK_INT >= P)
        navigationBarDividerColor = TRANSPARENT
    navigationBarColor = TRANSPARENT
}


context(Activity)
fun statusBarLightMode(enable: Boolean = true) {
    window.statusBarLightMode(enable)
}

/**
 * 设置亮色状态栏
 * @param enable 默认为亮色
 * https://qa.1r1g.com/sf/ask/4595541261/ 没有找到英文原文，
 * 这个更适合在页面有绘制的 View 的时候调用
 */
fun Window.statusBarLightMode(enable: Boolean = true) {
    if (SDK_INT >= R)
        insetsController?.apply {
            setSystemBarsAppearance(
                if (enable) APPEARANCE_LIGHT_STATUS_BARS else 0,
                APPEARANCE_LIGHT_STATUS_BARS
            )
        }
    statusBarLightOldMode(enable)
}

/**
 * 设置亮色状态栏（对应旧版本）
 * @param enable 默认为亮色
 */
@Suppress("DEPRECATION")
fun Window.statusBarLightOldMode(enable: Boolean = true) {
    decorView.apply {
        systemUiVisibility = if (enable)
            systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else
            systemUiVisibility xor SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}


context(Activity)
fun navigationBarLightMode(enable: Boolean = true) {
    window.navigationBarLightMode(enable)
}

/**
 * 设置亮色导航栏
 * @param enable 默认为亮色
 * 同上
 */

fun Window.navigationBarLightMode(enable: Boolean = true) {
    setNavigationBarTranslation()
    if (SDK_INT >= R)
        insetsController?.apply {
            setSystemBarsAppearance(
                if (enable) APPEARANCE_LIGHT_NAVIGATION_BARS else 0,
                APPEARANCE_LIGHT_NAVIGATION_BARS
            )
        }
    navigationBarLightOldMode(enable)
}

/**
 * 设置亮色导航栏（对应旧版本）
 * @param enable 默认为亮色
 */

@Suppress("DEPRECATION")
fun Window.navigationBarLightOldMode(enable: Boolean = true) {
    if (SDK_INT >= O)
        decorView.apply {
            systemUiVisibility = if (enable)
                systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            else
                systemUiVisibility xor SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
}