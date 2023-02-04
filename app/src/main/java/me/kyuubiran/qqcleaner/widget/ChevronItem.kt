package me.kyuubiran.qqcleaner.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ItemChevronBinding
import me.kyuubiran.qqcleaner.uitls.dp

/**
 * 左侧有前进按钮的子菜单项
 * @param context 对应的 Context 参数
 * @param attr 对应的 AttributeSet 参数
 */
class ChevronItem(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {


    private lateinit var text: String

    val binding = ItemChevronBinding.inflate(
        LayoutInflater.from(getContext()),
        this,
        true
    )

    init {
        initAttrs(attr)
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
        binding.chevronText.text = text
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Item)
        text = typedArray.getString(R.styleable.Item_text).toString()
        typedArray.recycle()
    }

    fun setTextColor(@ColorInt color: Int) {
        binding.chevronText.setTextColor(color)
    }

    fun setIconColor(@ColorInt color: Int) {
        binding.chevronImg.imageTintList = ColorStateList.valueOf(color)
    }
}