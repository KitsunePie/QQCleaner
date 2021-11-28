package me.kyuubiran.qqcleaner.ui.view.dialog.view

import android.content.Context
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View

/**
 * 获取按键事件
 * @author Agoines
 * @version 1.0
 */
class DialogBaseView(context: Context) : View(context) {

    /**
     * 按键事件监听器
     */
    private lateinit var onBackPressed: () -> Unit

    /**
     * DialogBaseRelativeLayout 获取焦点
     */
    fun setFocusable() {
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
    }

    /**
     * 劫持对应的点击事件，并完成对应效果
     */
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (isAttachedToWindow && event.action == ACTION_UP && event.keyCode == KEYCODE_BACK) {
            onBackPressed.invoke()
            return true
        }
        return false
    }

    /**
     * 设置按键事件监听器
     */
    fun setOnBackPressed(onBackPressed: () -> Unit) {
        this.onBackPressed = onBackPressed
    }

}