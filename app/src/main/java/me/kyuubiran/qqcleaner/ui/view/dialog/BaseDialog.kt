package me.kyuubiran.qqcleaner.ui.view.dialog

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.ViewGroup


/**
 * 弹窗基类
 * @author Agoines
 * @version 1.0
 */
abstract class BaseDialog(val context: Context) {

    val decorView: ViewGroup

    private val activity: Activity?

    /**
     * 弹窗显示
     */
    abstract fun show()

    /**
     * 弹窗关闭
     */
    abstract fun dismiss()

    /**
     * 获取 Activity 的实现
     *
     * @param context 上下文
     * @return Wrapper
     */
    private fun getWrapperActivity(context: Context): Activity? {
        if (context is Activity) {
            return context
        } else if (context is ContextWrapper) {
            return getWrapperActivity(context.baseContext)
        }
        return null
    }

    init {
        activity = getWrapperActivity(context)
        assert(activity != null)
        decorView = activity!!.window.decorView as ViewGroup
    }
}