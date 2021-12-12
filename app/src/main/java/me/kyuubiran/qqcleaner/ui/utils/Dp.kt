package me.kyuubiran.qqcleaner.ui.utils

import android.content.res.Resources

fun Float.dp2px(): Float {
    return 0.5f + this * Resources.getSystem().displayMetrics.density
}

fun Int.dp2px(): Float {
    return 0.5f + this * Resources.getSystem().displayMetrics.density
}