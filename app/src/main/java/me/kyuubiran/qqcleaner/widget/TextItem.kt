package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ItemTextBinding.*
import me.kyuubiran.qqcleaner.uitls.dp

class TextItem(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {
    public var subtext: String = "默认"

    private lateinit var text: String

    init {
        initAttrs(attr)
        val binding = inflate(
            LayoutInflater.from(getContext()),
            this,
            true
        )
        binding.root.apply {
            val background = GradientDrawable()
            background.setColor(Color.WHITE)
            background.cornerRadius = 10.dp
            this.background = RippleDrawable(
                ColorStateList.valueOf(Color.GRAY),
                null,
                background
            )
        }
        binding.textTitle.text = text
        binding.textSub.text = subtext
    }

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Item)
        text = typedArray.getString(R.styleable.Item_text).toString()
        typedArray.recycle()
    }
}