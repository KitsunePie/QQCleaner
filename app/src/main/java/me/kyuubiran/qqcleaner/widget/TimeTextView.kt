package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import me.kyuubiran.qqcleaner.uitls.getCurrentTimeText

/**
 * 展示时间的 TextView
 * @param context 对应的 Context 参数
 * @param attr 对应的 AttributeSet 参数
 */
class TimeTextView(context: Context, attr: AttributeSet) : AppCompatTextView(context, attr) {
    init {
        // 设置 text 为当前时间
        this.text = getCurrentTimeText()
    }
}