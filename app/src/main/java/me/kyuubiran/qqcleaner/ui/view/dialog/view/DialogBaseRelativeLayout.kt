package me.kyuubiran.qqcleaner.ui.view.dialog.view

import android.content.Context
import android.view.KeyEvent
import android.widget.RelativeLayout
import me.kyuubiran.qqcleaner.ui.view.dialog.interfaces.OnKeyPressedListener

/**
 * 获取按键事件
 * @author Agoines
 * @version 1.0
 */
class DialogBaseRelativeLayout(context: Context) : RelativeLayout(context) {

    /**
     * 按键事件监听器
     */
    private lateinit var onKeyPressedListener: OnKeyPressedListener

    /**
     * DialogBaseRelativeLayout 获取焦点
     */
    fun setFocusable() {
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (isAttachedToWindow && event.action == KeyEvent.ACTION_UP && event.keyCode == KeyEvent.KEYCODE_BACK) {
            onKeyPressedListener.onBackPressed()
            return true
        }
        return false
    }

    /**
     * 设置按键事件监听器
     */
    fun setOnKeyPressedListener(onKeyPressedListener: OnKeyPressedListener) {
        this.onKeyPressedListener = onKeyPressedListener
    }

}