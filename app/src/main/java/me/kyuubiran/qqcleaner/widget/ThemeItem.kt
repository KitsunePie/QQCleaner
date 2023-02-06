package me.kyuubiran.qqcleaner.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import me.kyuubiran.qqcleaner.R.drawable.ic_chosen
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.dpInt


class ThemeItem(context: Context, attr: AttributeSet) : AppCompatTextView(context, attr) {
    @Keep
    var itemBackgroundColor = Color.TRANSPARENT
        set(value) {
            field = value
            invalidate()
        }
    var checked: Boolean = false


    var itemTextColor = Color.TRANSPARENT
        set(value) {
            field = value
            invalidate()
        }

    private val backgroundPaint = Paint()

    var itemTextColorPress = Color.TRANSPARENT
        set(value) {
            field = value
            invalidate()
        }
    var itemBackgroundColorPress = Color.TRANSPARENT
        set(value) {
            field = value
            invalidate()
        }

    private val chosenDrawable = getDrawable(context, ic_chosen)!!.apply {
        setBounds(0, 0, 24.dpInt, 24.dpInt)
    }

    @Keep
    fun setIconDrawable(icon: Drawable) {
        icon.setBounds(0, 0, 24.dpInt, 24.dpInt)
        setCompoundDrawables(
            icon,
            null,
            if (checked) chosenDrawable else null,
            null
        )
    }
    @Keep
    fun setDrawableColor(@ColorInt color: Int) {
        TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(color))
    }

    override fun onDraw(canvas: Canvas?) {
        backgroundPaint.color = itemBackgroundColor
        canvas!!.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            10.dp,
            10.dp,
            backgroundPaint
        )
        super.onDraw(canvas)
    }

    fun setChecked(on: Boolean, hasAnim: Boolean) {
        checked = on
        if (hasAnim) {
            showAnimate()
        } else {
            initChecked()
        }

    }

    private fun initChecked() {
        itemBackgroundColor = if (checked) itemBackgroundColorPress else Color.TRANSPARENT
        setTextColor(if (checked) itemTextColorPress else itemTextColor)
        setDrawableColor(if (checked) itemTextColorPress else itemTextColor)
    }

    @Keep
    private fun showAnimate() {
        val backgroundAnimator = ObjectAnimator.ofArgb(
            this,
            "itemBackgroundColor",
            itemBackgroundColor,
            if (checked) itemBackgroundColorPress else Color.TRANSPARENT
        )

        val textAnimator = ObjectAnimator.ofArgb(
            this,
            "textColor",
            textColors.defaultColor,
            if (checked) itemTextColorPress else itemTextColor
        )

        val iconAnimator = ObjectAnimator.ofArgb(
            this,
            "drawableColor",
            textColors.defaultColor,
            if (checked) itemTextColorPress else itemTextColor
        )

        AnimatorSet().apply {
            playTogether(backgroundAnimator, textAnimator, iconAnimator)
            start()
        }
    }


}