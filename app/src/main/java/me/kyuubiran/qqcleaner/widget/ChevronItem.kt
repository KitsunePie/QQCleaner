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

/**
 * 左侧有前进按钮的子菜单项
 * @param context 对应的 Context 参数
 * @param attr 对应的 AttributeSet 参数
 */
class ChevronItem(context: Context, attr: AttributeSet) : AppCompatTextView(context, attr) {
    private val icon =
        AppCompatResources.getDrawable(context, R.drawable.ic_chevron_right)!!.apply {
            setBounds(0, 0, 24.dpInt, 24.dpInt)
        }

    init {
        setPadding(16.dpInt, 16.dpInt, 16.dpInt, 16.dpInt)
        gravity = Gravity.CENTER_VERTICAL
        isSingleLine = true
        compoundDrawablePadding = 16.dpInt
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
    }

    fun setRippleColor(@ColorInt color: Int) {
        this.background = rippleDrawable(
            color,
            null,
            shapeDrawable {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 10.dp
                setColor(Color.WHITE)
            }
        )
    }


    fun setIconColor(@ColorInt color: Int) {
        icon.setBounds(0, 0, 24.dpInt, 24.dpInt)
        setCompoundDrawables(
            null,
            null,
            icon,
            null
        )
        TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(color))
    }
}