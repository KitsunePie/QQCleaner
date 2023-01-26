package me.kyuubiran.qqcleaner.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import me.kyuubiran.qqcleaner.uitls.getCurrentTimeText

/**
 * 展示时间的 TextView
 * @param context 对应的 Context 参数
 * @param attr 对应的 AttributeSet 参数
 */
@SuppressLint("AppCompatCustomView")
class TimeTextView(context: Context, attr: AttributeSet) : TextView(context, attr) {
    init {
        // 设置 text 为当前时间
        this.text = getCurrentTimeText()

    }
}