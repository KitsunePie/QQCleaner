package me.kyuubiran.qqcleaner.utils

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEach

fun ViewGroup.findViewByText(text: String) =
    this.findViewByCondition {
        it.javaClass == TextView::class.java && (it as TextView).text == text
    }

fun ViewGroup.findViewByType(clazz: Class<*>) =
    this.findViewByCondition {
        it.javaClass == clazz
    }

fun ViewGroup.findViewByCondition(condition: (view: View) -> Boolean): View? {
    this.forEach {
        if (condition(it))
            return it
        val ret = if (it is ViewGroup) {
            it.findViewByCondition(condition)
        } else null
        ret?.let {
            return ret
        }
    }
    return null
}

