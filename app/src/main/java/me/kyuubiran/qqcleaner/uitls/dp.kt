package me.kyuubiran.qqcleaner.uitls

import android.content.Context
import android.view.View

/**
 * 在 View 的上下文中，通过 Int.dp 将数值转换为对应的大小的 dp
 */
context(View)
val Int.dp: Float
    get() = dp(context)


fun Int.dp(context: Context): Float {
    return 0.5f + this * context.resources.displayMetrics.density
}