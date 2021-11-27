package me.kyuubiran.qqcleaner.ui.view.dialog

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import me.kyuubiran.qqcleaner.ui.view.dialog.interfaces.OnKeyPressedListener
import me.kyuubiran.qqcleaner.ui.view.dialog.view.DialogBaseRelativeLayout


class MessageDialog(context: Context) : BaseDialog(context) {

    private val params = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    private var composeView = ComposeView(context)


    private val backgroundView = DialogBaseRelativeLayout(context).apply {
        this.addView(composeView)
    }

    private val onKeyPressedListener = object : OnKeyPressedListener {
        override fun onBackPressed() {
            dismiss()
        }
    }

    /**
     * 弹窗显示
     */
    override fun show() {
        // 在根布局添加弹窗
        decorView.addView(backgroundView, params)
        // 设置返回事件
        backgroundView.setOnKeyPressedListener(onKeyPressedListener)
        // 设置焦点
        backgroundView.setFocusable()
        // 设置点击事件，防止击穿
        backgroundView.setOnClickListener { }
        // 设置长按事件，防止击穿
        backgroundView.setOnLongClickListener { false }
        backgroundView.alpha = 0.0f
        backgroundView.animate()
            .setDuration(600)
            .alpha(1.0f)
            .setInterpolator(AccelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
    }

    fun setContent(content: @Composable () -> Unit) {
        composeView.setContent { content.invoke() }
    }

    /**
     * 弹窗关闭
     */
    override fun dismiss() {
        backgroundView.animate()
            .setDuration(600)
            .alpha(0.0f)
            .setInterpolator(DecelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    decorView.removeView(backgroundView)
                }
            })
    }
}