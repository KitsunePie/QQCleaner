package me.kyuubiran.qqcleaner.ui.view.dialog

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import me.kyuubiran.qqcleaner.ui.view.dialog.view.DialogBaseRelativeLayout

/**
 * 弹窗基类
 * @author Agoines
 * @version 1.0
 */
class BaseDialog(val context: Context) {

    private val decorView: ViewGroup

    private val activity: Activity?

    private val params = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    private var composeView = ComposeView(context)


    private val backgroundView = DialogBaseRelativeLayout(context)

    /**
     * 弹窗显示
     */
    fun show() {
        // 在根布局添加弹窗
        decorView.addView(backgroundView, params)
        decorView.addView(composeView)
        // 设置返回事件
        backgroundView.setOnKeyPressed { this.dismiss() }
        // 设置焦点
        backgroundView.setFocusable()
    }

    fun setContent(content: @Composable () -> Unit) {
        composeView.setContent {
            Box(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClickLabel = null,
                    // 防止击穿到 View 本体
                    onClick = { },
                    role = null,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }),
            ) {
                content.invoke()
            }
        }
    }

    /**
     * 弹窗关闭
     */
    fun dismiss() {
        decorView.removeView(composeView)
        decorView.removeView(backgroundView)
    }

    /**
     * 获取 Activity 的实现
     *
     * @param context 上下文
     * @return Wrapper
     */
    private fun getWrapperActivity(context: Context): Activity? {
        if (context is Activity)
            return context
        else
            if (context is ContextWrapper)
                return getWrapperActivity(context.baseContext)
        return null
    }

    init {
        activity = getWrapperActivity(context)
        decorView = activity!!.window.decorView as ViewGroup
    }
}

