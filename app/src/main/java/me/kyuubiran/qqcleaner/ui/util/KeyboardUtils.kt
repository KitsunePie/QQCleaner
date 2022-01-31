package me.kyuubiran.qqcleaner.ui.util

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager

/**
 * 回收键盘
 */
fun Activity.hideKeyBoard() {
    val imm =
        this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(this.window.decorView.windowToken, 0)
}