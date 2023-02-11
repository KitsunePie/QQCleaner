package me.kyuubiran.qqcleaner.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.dylanc.viewbinding.nonreflection.inflate
import group.infotech.drawable.dsl.shapeDrawable
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.DialogTopbarBinding
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

class DialogTopBar(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {
    private lateinit var text: String

    private val binding = inflate(DialogTopbarBinding::inflate)

    init {
        initAttrs(attr)
        binding.titleText.text = text
    }

    fun setIconOnClickListener(onClickListener: (View)-> Unit) {
        this.setOnClickListener(onClickListener)
    }

    fun setTitleColor(@ColorInt color: Int) {
        binding.titleText.setTextColor(color)
    }

    fun setTitle(title: String) {
        text = title
        binding.titleText.text = title
    }

    fun setIconColor(@ColorInt color: Int) {
        binding.backIcon.imageTintList = ColorStateList.valueOf(color)
    }

    fun setIconRippleColor(@ColorInt color: Int) {
        binding.backBackground.apply {
            background = rippleDrawable(
                color,
                null,
                shapeDrawable {
                    shape = GradientDrawable.OVAL
                    setColor(Color.WHITE)
                }
            )
        }
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(attrs: AttributeSet) {
        // 加载对应的 AttributeSet
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToolBar)
        // 获取对应 text 属性的内容
        text = typedArray.getString(R.styleable.ToolBar_title).toString()
        typedArray.recycle()
    }
}