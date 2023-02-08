package me.kyuubiran.qqcleaner.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.dylanc.viewbinding.nonreflection.inflate
import group.infotech.drawable.dsl.shapeDrawable
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ItemSwitchBinding
import me.kyuubiran.qqcleaner.uitls.dp
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

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

    private val binding = inflate(ItemSwitchBinding::inflate)

    init {
        initAttrs(attr)

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


    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.Item)
        text = typedArray.getString(R.styleable.Item_text).toString()
        typedArray.recycle()
    }

}