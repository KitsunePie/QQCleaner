package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import me.kyuubiran.qqcleaner.uitls.dpInt

class DialogIconItem(context: Context, attr: AttributeSet) : AppCompatTextView(context, attr) {

    init {
        compoundDrawablePadding = 16.dpInt
        setPadding(16.dpInt, 16.dpInt, 16.dpInt, 16.dpInt)
        gravity = Gravity.CENTER_VERTICAL
        isSingleLine = true
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
    }

    fun setDrawableColor(@ColorInt color: Int) {
        TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(color))
    }
}