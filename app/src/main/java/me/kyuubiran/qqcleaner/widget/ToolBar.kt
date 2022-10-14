package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.view.updateLayoutParams
import me.kyuubiran.qqcleaner.databinding.ToolbarBinding


class ToolBar(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {
    init{
        val binding = ToolbarBinding.inflate(
            LayoutInflater.from(getContext()),
            this,
            true
        )
        binding.root.rootView.setOnApplyWindowInsetsListener { _, insets ->
            this.updateLayoutParams {
                (this as LayoutParams).topMargin =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.systemBars()).top
                    else insets.systemWindowInsetTop
            }
            insets
        }
    }
}