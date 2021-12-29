package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import me.kyuubiran.qqcleaner.ui.composable.dialog.view.DialogBaseView

open class BaseDialog(context: Context) {

    private var isBackPressBackDismiss: Boolean = true

    private val decorView: ViewGroup

    private val context: Context

    private lateinit var contentView: View

    private val dialogBaseView = DialogBaseView(context).apply {
        this.setFocusable()
        this.setOnClickListener {
        }
        setOnBackPressed {
            if (isBackPressBackDismiss) {
                dismiss()
            }
        }
    }

    init {
        this.context = context
        this.decorView = (context as Activity).window.decorView as ViewGroup
    }

    fun setContentView(view: View) {
        this.contentView = view
    }

    fun show() {
        decorView.addView(dialogBaseView)
        decorView.addView(contentView)
    }

    open fun dismiss() {

    }

    fun removeView() {
        decorView.removeView(dialogBaseView)
        decorView.removeView(contentView)
    }

    fun setDismissOnBackPress(isDismiss: Boolean) {
        this.isBackPressBackDismiss = isDismiss
    }

}

