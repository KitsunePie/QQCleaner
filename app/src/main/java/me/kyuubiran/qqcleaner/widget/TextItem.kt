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
import me.kyuubiran.qqcleaner.databinding.ItemTextBinding.*
import me.kyuubiran.qqcleaner.uitls.dp

/**
 * 展示 Text 的子菜单
 * @param context 对应的 Context 参数
 * @param attr 对应的 AttributeSet 参数
 */
class TextItem(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {

    // 菜单小字对应的文本
    var subtext: String = "默认"

    private lateinit var text: String
    val binding = inflate(LayoutInflater.from(getContext()), this, true)

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
        binding.textTitle.text = text
        binding.textSub.text = subtext
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(attrs: AttributeSet) {
        // 加载对应的 AttributeSet
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Item)
        // 获取对应 text 属性的内容
        text = typedArray.getString(R.styleable.Item_text).toString()
        typedArray.recycle()
    }

    fun setTextColor(@ColorInt color: Int) {
        binding.textTitle.setTextColor(color)
    }

    fun setTipTextColor(@ColorInt color: Int) {
        binding.textSub.setTextColor(color)
    }
}