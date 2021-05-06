package me.kyuubiran.qqcleaner.utils

import android.view.View
import android.view.ViewGroup

internal fun ViewGroup.findViewByType(clazz: Class<*>): View? =
    this.findViewByCondition {
        it.javaClass == clazz
    }

@Suppress("UNCHECKED_CAST")
internal fun <T : View> ViewGroup.findViewByCondition(condition: (view: View) -> Boolean): T? {
    this.forEach {
        if (condition(it))
            return it as T
        val ret = if (it is ViewGroup) {
            it.findViewByCondition<T>(condition)
        } else null
        ret?.let {
            return ret
        }
    }
    return null
}
