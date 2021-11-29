package me.kyuubiran.qqcleaner.ui.view.dialog

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import me.kyuubiran.qqcleaner.ui.utils.fillMaxModifier
import me.kyuubiran.qqcleaner.ui.view.dialog.view.DialogBaseView


/**
 * 弹窗基类
 * @author Agoines
 * @version 1.0
 */
class BaseDialog(val context: Context) {

    /**
     * isClickDismiss 是否可以通过返回关闭弹窗
     */
    private var isBackDismiss = true

    /**
     * isClickDismiss 是否可以通过点击关闭弹窗
     */
    private var isClickDismiss = true

    /**
     * decorView Activity 对应的 decorView
     */
    private val decorView: ViewGroup

    /**
     * activity 对应依附 Activity
     */
    private val activity: Activity?

    /**
     * params 是布局大小
     */
    private val params = LayoutParams(MATCH_PARENT, MATCH_PARENT)

    /**
     * composeView 是 Compose 所需布局
     */
    private var composeView = ComposeView(context)

    /**
     * background 是 劫持事件所需布局
     */
    private val backgroundView = DialogBaseView(context)

    private var isLightStatusBar = true

    private var isLightNavigationBar = true

    private var dismissBlock = {}
    /**
     * 弹窗显示
     */
    fun show() {
        // 在根布局添加弹窗
        decorView.addView(backgroundView, params)
        // 添加一个 composeView 方便添加 compose 布局
        decorView.addView(composeView, params)
        // 设置返回事件
        backgroundView.setOnBackPressed { this.dismiss() }
        // 设置焦点
        backgroundView.setFocusable()
    }

    /**
     * 设置 Compose 对应布局
     * @param content 对应的 compose 布局
     */
    fun setContent(content: @Composable () -> Unit) {
        composeView.setContent {
            Box(
                // 填充满整个整个页面
                modifier = fillMaxModifier.clickable(
                    enabled = true,
                    onClickLabel = null,
                    // 防止击穿到 View 本体
                    onClick = {
                        // 是否点击后关闭后关闭弹窗
                        if (isClickDismiss) dismiss()
                    },
                    role = null,
                    // 去掉点击水波纹
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }),
            ) {
                // 对应的 compose 布局
                content.invoke()
            }
        }
    }

    /**
     * 设置返回可以关闭弹窗
     * @param backToDismiss 返回背景是否会关闭弹窗
     */
    fun backToDismiss(backToDismiss: Boolean) {
        isBackDismiss = backToDismiss
    }

    /**
     * 设置点击背景可以关闭弹窗
     * @param clickToDismiss 点击背景是否会关闭弹窗
     */
    fun clickToDismiss(clickToDismiss: Boolean) {
        isClickDismiss = clickToDismiss
    }


    fun dismissBlock(dismissBlock: () -> Unit) {
        this.dismissBlock = dismissBlock
    }

    /**
     * 弹窗关闭
     */
    private fun dismiss() {
        dismissBlock.invoke()
    }

    /**
     * 删除对应的 View
     */
    fun removeView() {
        decorView.removeView(composeView)
        decorView.removeView(backgroundView)
    }

    fun setStatusBarLightMode(isLight: Boolean) {
        this.isLightStatusBar = isLight
    }

    fun setNavigationBarLightMode(isLight: Boolean) {
        this.isLightNavigationBar = isLight
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
        // 给 activity 赋值
        activity = getWrapperActivity(context)
        // 设置 decorView 赋值
        decorView = activity!!.window.decorView as ViewGroup
    }
}

