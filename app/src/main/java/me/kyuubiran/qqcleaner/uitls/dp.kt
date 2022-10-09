package me.kyuubiran.qqcleaner.uitls

import android.view.View

context(View)
val Int.dp: Float get() = 0.5f + this * context.resources.displayMetrics.density