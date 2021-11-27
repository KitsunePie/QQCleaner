package me.kyuubiran.qqcleaner.ui.view.dialog

import android.content.Context


fun messageDialog(context: Context) {
    BaseDialog(context = context).apply {
        this.setContent {
        }
        this.show()
    }
}