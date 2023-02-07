package me.kyuubiran.qqcleaner.uitls

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment

context(Fragment)
fun getNavigationBarHeight(): Int {
    return requireContext().getNavigationBarHeight()
}

@SuppressLint("DiscouragedApi", "InternalInsetResource")
private fun Context.getNavigationBarHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

context(Fragment)
fun checkDeviceHasNavigationBar(): Boolean {
    return requireContext().checkDeviceHasNavigationBar()
}

@SuppressLint("PrivateApi", "DiscouragedApi")
fun Context.checkDeviceHasNavigationBar(): Boolean {
    var hasNavigationBar = false
    val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    if (id > 0) {
        hasNavigationBar = resources.getBoolean(id)
    }
    try {
        val systemPropertiesClass = Class.forName("android.os.SystemProperties")
        val m = systemPropertiesClass.getMethod("get", String::class.java)
        val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
        if ("1" == navBarOverride) {
            hasNavigationBar = false
        } else if ("0" == navBarOverride) {
            hasNavigationBar = true
        }
    } catch (_: Exception) {
    }
    return hasNavigationBar
}
