package me.kyuubiran.qqcleaner.ui.view.dialog.view

import android.content.Context
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK
import android.widget.RelativeLayout

/**
 * 获取按键事件
 * @author Agoines
 * @version 1.0
 */
class DialogBaseRelativeLayout(context: Context) : RelativeLayout(context) {

    /**
     * 按键事件监听器
     */
    private lateinit var onKeyPressed: () -> Unit

    /**
     * DialogBaseRelativeLayout 获取焦点
     */
    fun setFocusable() {
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (isAttachedToWindow && event.action == ACTION_UP && event.keyCode == KEYCODE_BACK) {
            onKeyPressed.invoke()
            return true
        }
        return false
    }

    /**
     * 设置按键事件监听器
     */
    fun setOnKeyPressed(onKeyPressed: () -> Unit) {
        this.onKeyPressed = onKeyPressed
    }

}