package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import android.view.WindowInsets
import androidx.core.view.WindowCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity
import me.kyuubiran.qqcleaner.databinding.BaseDialogBinding
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.theme.Theme
import me.kyuubiran.qqcleaner.uitls.navigationBarLightOldMode
import me.kyuubiran.qqcleaner.uitls.setNavigationBarTranslation
import me.kyuubiran.qqcleaner.uitls.setStatusBarTranslation
import me.kyuubiran.qqcleaner.uitls.statusBarLightOldMode



open class BaseDialog(private val states: MainActivity.MainActivityStates) : DialogFragment() {

    lateinit var layout: View

    lateinit var dialogLayout: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 随便设置一个空主题，这是一个带动画的主题
        val dialog = Dialog(requireContext(), android.R.style.Animation_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = BaseDialogBinding.inflate(layoutInflater)

        dialogLayout = binding.dialogLayout
        binding.dialogLayout.addView(
            layout,
            0,
            LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        )

        binding.dialogLayout.setOnClickListener {
            states.setTheme(Theme.AUTO_THEME, false, requireContext())
        }

        // 内部填充
        @Suppress("DEPRECATION")
        binding.navMargin.setOnApplyWindowInsetsListener { _, insets ->
            binding.navMargin.updateLayoutParams {
                height =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    else insets.systemWindowInsetBottom
            }
            insets
        }


        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(false)

        dialog.window!!.apply {
            WindowCompat.setDecorFitsSystemWindows(this, false)
            // 设置导航栏透明
            setStatusBarTranslation()
            setNavigationBarTranslation()


            lifecycleScope.launch {
                states.theme.collect {
                    statusBarLightOldMode(it == LightColorPalette)
                    navigationBarLightOldMode(it == LightColorPalette)
                    binding.dialogLayout.background =
                        ColorDrawable(states.theme.value.dialogBackgroundColor)
                    setBackgroundDrawable(ColorDrawable(states.theme.value.maskColor))
                }
            }

            val wlp = attributes
            //设置窗口的宽度为 MATCH_PARENT,效果是和屏幕宽度一样大
            wlp.width = MATCH_PARENT
            wlp.height = MATCH_PARENT

            attributes = wlp
        }
        return dialog
    }
}