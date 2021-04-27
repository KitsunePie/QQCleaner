package me.kyuubiran.qqcleaner.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.github.kyuubiran.ezxhelper.utils.runOnMainThread
import me.kyuubiran.qqcleaner.ui.view.ToastView
import java.lang.ref.WeakReference

object ToastX {

    fun showToastX(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        show(context, message, duration)
    }

    fun showToastX(context: Context, res: Int, duration: Int = Toast.LENGTH_SHORT) {
        show(context, context.getString(res), duration)
    }

    @SuppressLint("InflateParams")
    private fun show(context: Context, message: String, duration: Int) {
        WeakReference(context).get()?.let {
            val view = ToastView(it).apply {
                this.message.text = message
            }

            Toast(it).apply {
                setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, 0, 200)
                this.duration = duration
                this.view = view
            }.show()
        }
    }
}

fun Context.showToastX(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    runOnMainThread {
        ToastX.showToastX(this, msg, duration)
    }
}