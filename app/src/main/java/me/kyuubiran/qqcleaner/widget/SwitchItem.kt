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
import me.kyuubiran.qqcleaner.databinding.ItemSwitchBinding
import me.kyuubiran.qqcleaner.uitls.dp

/**
 * 有 Switch 开关的菜单项
 * @param context 对应的 Context 参数
 * @param attr 对应的 AttributeSet 参数
 */
class SwitchItem(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {

    private var checked: Boolean = false

    private var isWhite: Boolean = false

    private var isDark: Boolean = false


    lateinit var text: String

    private lateinit var listener: (Boolean) -> Unit

    val binding = ItemSwitchBinding.inflate(
        LayoutInflater.from(getContext()), this, true
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

        // 设置默认值，防止为空
        binding.switchText.text = text
        binding.switchImg.setChecked(checked, isWhite, isDark, false)

        // 设置点击事件
        this.setOnClickListener {
            checked = !checked
            binding.switchImg.setChecked(checked, isWhite, isDark, true)
            listener(checked)
        }
    }

    fun setTextColor(color: Int) {
        binding.switchText.setTextColor(color)
    }

    fun setSwitchChecked(on: Boolean) {
        if (on != checked) {
            checked = on
            binding.switchImg.setChecked(on, isWhite, isDark, false)
        }
    }

    fun setSwitchColor(white: Boolean, dark: Boolean) {
        isWhite = white
        isDark = dark
        binding.switchImg.setChecked(checked, isWhite, isDark, false)
    }


    fun setSwitchListener(listener: (Boolean) -> Unit) {
        this.listener = listener
    }


    private fun initAttrs(attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.Item)
        text = typedArray.getString(R.styleable.Item_text).toString()
        typedArray.recycle()
    }

}