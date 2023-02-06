package me.kyuubiran.qqcleaner.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import group.infotech.drawable.dsl.shapeDrawable
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ItemTextBinding.*
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

/**
 * 展示 Text 的子菜单
 * @param context 对应的 Context 参数
 * @param attr 对应的 AttributeSet 参数
 */
class TextItem(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {

    // 菜单小字对应的文本
    val defaultText: String = "默认"

    private lateinit var text: String

    private val binding = inflate(LayoutInflater.from(getContext()), this, true)

    init {
        initAttrs(attr)
        binding.textTitle.text = text
        binding.textSub.text = defaultText
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(attrs: AttributeSet) {
        // 加载对应的 AttributeSet
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Item)
        // 获取对应 text 属性的内容
        text = typedArray.getString(R.styleable.Item_text).toString()
        typedArray.recycle()
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

    fun setTextColor(@ColorInt color: Int) {
        binding.textTitle.setTextColor(color)
    }

    fun setTipTextColor(@ColorInt color: Int) {
        binding.textSub.setTextColor(color)
    }

    fun setTipText(text: String) {
        binding.textSub.text = text
    }
}