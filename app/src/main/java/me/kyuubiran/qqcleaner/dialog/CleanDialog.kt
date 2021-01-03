package me.kyuubiran.qqcleaner.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import me.kyuubiran.qqcleaner.utils.CleanManager

const val HALF_MODE = 0
const val FULL_MODE = 1
const val CUSTOMER_MODE = 2

object CleanDialog {
    private fun getMessage(mode: Int): String {
        return when (mode) {
            HALF_MODE -> "一键瘦身"
            FULL_MODE -> "完全瘦身"
            CUSTOMER_MODE -> "自定义瘦身"
            else -> ""
        }
    }

    //显示确认瘦身时的dialog
    fun showConfirmDialog(mode: Int, context: Context) {
        val msg = getMessage(mode)
        if (msg.isEmpty()) throw IllegalAccessException("你不对劲 你有问题")
        AlertDialog.Builder(context)
            .setTitle("注意")
            .setMessage("你确定要执行${msg}吗?")
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
