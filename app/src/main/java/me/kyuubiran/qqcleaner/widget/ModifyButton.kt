package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import group.infotech.drawable.dsl.shapeDrawable
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.dpInt
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

class ModifyButton(context: Context, attr: AttributeSet) : AppCompatTextView(context, attr) {
    var modify: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                modifyBackground()
            }
        }
    var buttonBackgroundColor = Color.TRANSPARENT

    var buttonBackgroundModifyColor = Color.TRANSPARENT

    var buttonRippleColor = Color.TRANSPARENT

    var buttonTextColor = Color.TRANSPARENT

    var buttonTextModifyColor = Color.TRANSPARENT

    init {
        setPadding(16.dpInt, 16.dpInt, 16.dpInt, 16.dpInt)
        gravity = Gravity.CENTER
        isSingleLine = true
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
    }

    fun modifyBackground() {
        this.setTextColor(if (modify) buttonTextModifyColor else buttonTextColor)
        this.background = rippleDrawable(
            if (modify) buttonRippleColor else Color.TRANSPARENT,
            shapeDrawable {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 10.dp
                setColor(if (modify) buttonBackgroundModifyColor else buttonBackgroundColor)
            },
            shapeDrawable {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 10.dp
                setColor(Color.WHITE)
            }
        )
    }
}