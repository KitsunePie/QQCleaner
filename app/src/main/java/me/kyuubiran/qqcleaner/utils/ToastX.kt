package me.kyuubiran.qqcleaner.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.github.kyuubiran.ezxhelper.utils.runOnMainThread
import me.kyuubiran.qqcleaner.ui.view.ToastView
import java.lang.ref.WeakReference

object ToastX {

    fun show(context: Context, message: String) {
        show(context, message, Toast.LENGTH_SHORT)
    }

    fun show(context: Context, res: Int) {
        show(context, context.getString(res), Toast.LENGTH_SHORT)
    }

    fun showLong(context: Context, message: String) {
        show(context, message, Toast.LENGTH_LONG)
    }

    fun showLong(context: Context, res: Int) {
        show(context, context.getString(res), Toast.LENGTH_LONG)
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

fun Context.show(msg: String) {
    runOnMainThread {
        ToastX.show(this, msg)
    }
}

fun Context.showLong(msg: String) {
    runOnMainThread {
        ToastX.showLong(this, msg)
    }
}