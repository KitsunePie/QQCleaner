package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import me.kyuubiran.qqcleaner.databinding.BaseDialogBinding
import me.kyuubiran.qqcleaner.uitls.navigationBarLightOldMode
import me.kyuubiran.qqcleaner.uitls.setNavigationBarTranslation
import me.kyuubiran.qqcleaner.uitls.setStatusBarTranslation
import me.kyuubiran.qqcleaner.uitls.statusBarLightOldMode


class BaseDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = BaseDialogBinding.inflate(requireActivity().layoutInflater)
        // 随便设置一个空主题，这是一个带动画的主题
        val dialog = Dialog(requireContext(), android.R.style.Animation_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.setCanceledOnTouchOutside(false)

        dialog.window!!.apply {
            WindowCompat.setDecorFitsSystemWindows(this, false)
            // 设置导航栏透明
            setStatusBarTranslation()
            setNavigationBarTranslation()
            statusBarLightOldMode()
            navigationBarLightOldMode()
            setBackgroundDrawableResource(android.R.color.transparent)
            val wlp = attributes
            //设置窗口的宽度为 MATCH_PARENT,效果是和屏幕宽度一样大
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT
            wlp.height = WindowManager.LayoutParams.MATCH_PARENT

            attributes = wlp
        }
        return dialog
    }
}