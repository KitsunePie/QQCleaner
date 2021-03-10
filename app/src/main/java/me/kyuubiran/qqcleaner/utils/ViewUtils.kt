package me.kyuubiran.qqcleaner.utils

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEach
import java.util.*

internal inline fun <reified T : TextView> ViewGroup.findViewByText(
    text: String,
    contains: Boolean = false,
    ignoreCase: Boolean = false
): T? =
    this.findViewByCondition {
        val _text = if (ignoreCase) text.toLowerCase(Locale.ROOT) else text
        it.javaClass == T::class.java && if (!contains) (it as TextView).text.toString()
            .let { s ->
                if (ignoreCase) s.toLowerCase(Locale.ROOT) else s
            } == _text else (it as TextView).text.toString().let { s ->
            if (ignoreCase) s.toLowerCase(Locale.ROOT) else s
        }.contains(
            _text
        )
    }

internal fun ViewGroup.findViewByType(clazz: Class<*>): View? =
    this.findViewByCondition {
        it.javaClass == clazz
    }

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

internal inline fun <reified T : TextView> ViewGroup.findViewByText(
    vararg text: String,
    contains: Boolean = false,
    ignoreCase: Boolean = false
): T? {
    for (str in text) {
        val v = this.findViewByText<T>(str, contains = contains, ignoreCase = ignoreCase)
        v?.let {
            return v
        }
    }
    return null
}