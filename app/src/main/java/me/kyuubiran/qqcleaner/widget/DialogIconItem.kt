package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import group.infotech.drawable.dsl.shapeDrawable
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.dpInt
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

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

    fun setRippleColor(@ColorInt color: Int) {
        setRippleColor(color, Color.TRANSPARENT)
    }

    private fun setRippleColor(@ColorInt color: Int, contentColor: Int) {
        background = rippleDrawable(
            color,
            shapeDrawable {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 10.dp
                setColor(contentColor)
            },
            shapeDrawable {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 10.dp
                setColor(Color.WHITE)
            }
        )
    }
}