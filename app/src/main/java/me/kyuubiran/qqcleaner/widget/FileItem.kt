package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import group.infotech.drawable.dsl.shapeDrawable
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.dpInt
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

class FileItem(context: Context, attr: AttributeSet?) : AppCompatTextView(context, attr) {
    private val drawable = AppCompatResources.getDrawable(context, R.drawable.ic_file)!!.apply {
        setBounds(0, 0, 24.dpInt, 24.dpInt)
    }
    init {
        compoundDrawablePadding = 16.dpInt
        setPadding(16.dpInt, 18.dpInt, 16.dpInt, 18.dpInt)
        gravity = Gravity.CENTER_VERTICAL
        isSingleLine = true
        setCompoundDrawables(
            drawable,
            null,
            null,
            null
        )
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
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