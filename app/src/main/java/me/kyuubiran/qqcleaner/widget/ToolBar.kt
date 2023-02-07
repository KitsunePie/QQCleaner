package me.kyuubiran.qqcleaner.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.WindowInsets
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import group.infotech.drawable.dsl.shapeDrawable
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.databinding.ToolbarBinding
import me.kyuubiran.qqcleaner.uitls.rippleDrawable

/**
 * 子页面的顶栏
 * @param context 对应的 Context 参数
 * @param attr 对应的 AttributeSet 参数
 */
class ToolBar(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {
    private lateinit var text: String

    private val binding = ToolbarBinding.inflate(
        LayoutInflater.from(getContext()), this, true
    )

    init {
        initAttrs(attr)
        binding.titleText.text = text
        binding.backBackground.apply {
            setOnClickListener {
                val fragment =
                    (this.context as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
                val navController = NavHostFragment.findNavController(fragment)
                navController.popBackStack()
            }
        }

        // 顶栏边距
        @Suppress("DEPRECATION")
        binding.root.rootView.setOnApplyWindowInsetsListener { _, insets ->
            this.updateLayoutParams<MarginLayoutParams> {
                topMargin =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.systemBars()).top
                    else insets.systemWindowInsetTop
            }
            insets
        }
    }

    fun setTitleColor(@ColorInt color: Int) {
        binding.titleText.setTextColor(color)
    }
    fun setIconRippleColor(@ColorInt color: Int){
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
    private fun initAttrs(attrs: AttributeSet) {
        // 加载对应的 AttributeSet
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToolBar)
        // 获取对应 text 属性的内容
        text = typedArray.getString(R.styleable.ToolBar_title).toString()
        typedArray.recycle()
    }
}