package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.WindowInsets
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import me.kyuubiran.qqcleaner.databinding.ToolbarBinding

/**
 * 子页面的顶栏
 * @param context 对应的 Context 参数
 * @param attr 对应的 AttributeSet 参数
 */
class ToolBar(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {
    init {
        // 和对应的 xml 进行绑定
        val binding = ToolbarBinding.inflate(
            LayoutInflater.from(getContext()),
            this,
            true
        )

        // 顶栏边距
        binding.root.rootView.setOnApplyWindowInsetsListener { _, insets ->
            this.updateLayoutParams {
                (this as LayoutParams).topMargin =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.systemBars()).top
                    else @Suppress("DEPRECATION") insets.systemWindowInsetTop
            }
            insets
        }
    }
}