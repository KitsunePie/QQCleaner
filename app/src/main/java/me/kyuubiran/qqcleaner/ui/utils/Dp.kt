package me.kyuubiran.qqcleaner.ui.utils

import android.content.Context

fun Float.dp2px(context: Context): Float {
    return 0.5f + this * context.resources.displayMetrics.density
}

fun Int.dp2px(context: Context): Float {
    return 0.5f + this * context.resources.displayMetrics.density
}