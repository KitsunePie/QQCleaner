package me.kyuubiran.qqcleaner.uitls

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment

context(View)
val Int.dp: Float
    get() = dp(context)

context(Fragment)
val Int.dp: Float
    get() = dp(requireContext())

context(View, Fragment)
val Int.dp: Float
    get() = dp(requireContext())

context(Fragment)
val Int.dpInt: Int
    get() = dpInt(requireContext())
context(View)
val Int.dpInt: Int
    get() = dpInt(context)

context(View, Fragment)
val Int.dpInt: Int
    get() = dpInt(requireContext())

fun Int.dpInt(context: Context): Int {
    return this.dp(context).toInt()
}

fun Int.dp(context: Context): Float {
    return 0.5f + this * context.resources.displayMetrics.density
}