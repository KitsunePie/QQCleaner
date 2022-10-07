package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import me.kyuubiran.qqcleaner.uitls.getCurrentTimeText


class TimeTextView(context: Context, attr: AttributeSet) : AppCompatTextView(context, attr){
    init {
        this.text = getCurrentTimeText()

    }
}