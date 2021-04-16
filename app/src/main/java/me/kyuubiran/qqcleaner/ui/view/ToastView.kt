package me.kyuubiran.qqcleaner.ui.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.kyuubiran.qqcleaner.R

class ToastView(context: Context, set: AttributeSet? = null) : BViewGroup(context) {

    val message = TextView(context).apply {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        maxWidth = 300.dp
        gravity = Gravity.CENTER
        setTextColor(Color.BLACK)
        val padding = 12.dp
        setPadding(padding, padding, padding, padding)
        setBackgroundResource(R.drawable.bg_toast)
        text = context.getText(R.string.app_name)
        addView(this)
    }

    private val icon = ImageView(context).apply {
        layoutParams = LayoutParams(60.dp, 60.dp)
        setImageResource(R.mipmap.ic_launcher_foreground)
        addView(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        message.autoMeasure()
        icon.autoMeasure()
        setMeasuredDimension(message.measuredWidth, message.measuredHeight + icon.measuredHeight / 2)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        icon.let { it.layout(it.toHorizontalCenter(this), 0) }
        message.layout(0, icon.measuredHeight / 2)
    }
}