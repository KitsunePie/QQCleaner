package me.kyuubiran.qqcleaner.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import me.kyuubiran.qqcleaner.utils.CleanManager

const val HALF_MODE = 0
const val FULL_MODE = 1
const val CUSTOMER_MODE = 2

object CleanDialog {
    fun showConfirmDialog(mode: Int, context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Tips")
            .setMessage("你确定要清除吗?")
            .setNegativeButton("取消") { _, _ -> }
            .setPositiveButton("确定") { _, _ ->
                when (mode) {
                    HALF_MODE -> CleanManager.halfClean()
                    FULL_MODE -> CleanManager.fullClean()
                    CUSTOMER_MODE -> CleanManager.customerClean()
                }
            }
            .create()
            .show()
    }
}
