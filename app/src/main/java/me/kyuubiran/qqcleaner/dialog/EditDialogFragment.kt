package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type


open class EditDialogFragment : BaseDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)

        // 实现 edge to edge 动画
        ViewCompat.setWindowInsetsAnimationCallback(
            dialogLayout,
            object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {

                var startBottom = 0f

                var endBottom = 0f

                override fun onPrepare(
                    animation: WindowInsetsAnimationCompat
                ) {
                    startBottom = dialogLayout.bottom.toFloat()
                }

                override fun onStart(
                    animation: WindowInsetsAnimationCompat,
                    bounds: WindowInsetsAnimationCompat.BoundsCompat
                ): WindowInsetsAnimationCompat.BoundsCompat {
                    endBottom = dialogLayout.bottom.toFloat()
                    return bounds
                }


                override fun onProgress(
                    insets: WindowInsetsCompat,
                    runningAnimations: MutableList<WindowInsetsAnimationCompat>
                ): WindowInsetsCompat {
                    val imeAnimation = runningAnimations.find {
                        it.typeMask and Type.ime() != 0
                    } ?: return insets

                    dialogLayout.translationY =
                        (startBottom - endBottom) * (1 - imeAnimation.interpolatedFraction)

                    return insets
                }
            }
        )
        return dialog
    }

}