package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import group.infotech.drawable.dsl.shapeDrawable
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.DeveloperItemBinding
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

/**
 * 开发者页面对应菜单项
 * @param context 对应的 Context 参数
 * @param attr 对应的 AttributeSet 参数
 */
class DeveloperItem(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {
    lateinit var text: String
    lateinit var name: String
    private lateinit var image: Drawable

    private val binding = DeveloperItemBinding.inflate(
        LayoutInflater.from(getContext()),
        this,
        true
    )

    init {
        initAttrs(attr)
        binding.developerName.text = name
        binding.developerText.text = text
        binding.developerImage.setImageDrawable(image)
    }

    fun setRippleColor(@ColorInt color: Int) {
        binding.root.background = rippleDrawable(
            color,
            null,
            shapeDrawable {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 10.dp
                setColor(Color.WHITE)
            }
        )
    }

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.DeveloperItem)
        text = typedArray.getString(R.styleable.DeveloperItem_text).toString()
        name = typedArray.getString(R.styleable.DeveloperItem_name).toString()
        image = typedArray.getDrawable(R.styleable.DeveloperItem_src)!!
        typedArray.recycle()
    }

    fun setTextColor(@ColorInt color: Int) {
        binding.developerText.setTextColor(color)
    }
}