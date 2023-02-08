package me.kyuubiran.qqcleaner.dialog

import android.animation.Animator
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity
import me.kyuubiran.qqcleaner.databinding.BaseDialogBinding
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.uitls.checkDeviceHasNavigationBar
import me.kyuubiran.qqcleaner.uitls.getNavigationBarHeight
import me.kyuubiran.qqcleaner.uitls.navigationBarLightMode
import me.kyuubiran.qqcleaner.uitls.setNavigationBarTranslation
import me.kyuubiran.qqcleaner.uitls.setStatusBarTranslation
import me.kyuubiran.qqcleaner.uitls.statusBarLightMode


open class BaseDialog(val model: MainActivity.MainActivityStates) : DialogFragment() {

    lateinit var layout: View

    lateinit var dialogLayout: View

    private var height: Float = 0f

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

        // 内部填充
        @Suppress("DEPRECATION")
        binding.navMargin.setOnApplyWindowInsetsListener { _, insets ->
            binding.navMargin.updateLayoutParams {
                height =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    else if (checkDeviceHasNavigationBar()) getNavigationBarHeight() else 0
            }

            binding.dialogLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                        insets.getInsets(WindowInsets.Type.ime()).bottom
                    else
                        insets.systemWindowInsetBottom
            }
            insets
        }

        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(false)

        dialog.window!!.apply {
            WindowCompat.setDecorFitsSystemWindows(this, false)
            @Suppress("DEPRECATION")
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            // 设置导航栏透明
            setStatusBarTranslation()
            setNavigationBarTranslation()

            lifecycleScope.launch {
                model.colorPalette.collect {
                    statusBarLightMode(it == LightColorPalette)
                    navigationBarLightMode(it == LightColorPalette)
                    binding.dialogLayout.setBackgroundColor(it.dialogBackgroundColor)
                    setBackgroundDrawable(ColorDrawable(it.maskColor))
                }
            }

            val wlp = attributes
            //设置窗口的宽度为 MATCH_PARENT,效果是和屏幕宽度一样大
            wlp.width = MATCH_PARENT
            wlp.height = MATCH_PARENT

            attributes = wlp
        }

        fun dialogEnterAnim(height: Float) {
            this.height = height
            dialogLayout.translationY = height
            dialogLayout.animate()
                .translationY(0f)
        }

        val viewTreeObserver = dialogLayout.viewTreeObserver
        val heightListener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (dialogLayout.height != 0) {
                    dialogEnterAnim(dialogLayout.height.toFloat())
                    if (viewTreeObserver.isAlive) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            }
        }
        viewTreeObserver.addOnGlobalLayoutListener(heightListener)

        return dialog
    }

    override fun onResume() {
        super.onResume()
        dialog!!.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                if (event!!.action != KeyEvent.ACTION_DOWN) true else {
                    dialogLayout.animate()
                        .translationY(height)
                        .setListener(object : Animator.AnimatorListener{
                            override fun onAnimationStart(animation: Animator) {
                            }

                            override fun onAnimationEnd(animation: Animator) {
                                dialog.dismiss()
                            }

                            override fun onAnimationCancel(animation: Animator) {
                            }

                            override fun onAnimationRepeat(animation: Animator) {

                            }

                        })
                        .start()
                    true // pretend we've processed it
                }
            } else false // pass on to be processed as normal
        }
    }

    open class StateHolder : ViewModel()
}