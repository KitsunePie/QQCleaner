package me.kyuubiran.qqcleaner.dialog

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import me.kyuubiran.qqcleaner.utils.CleanManager
import me.kyuubiran.qqcleaner.utils.ConfigManager
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CLEAN_DELAY
import me.kyuubiran.qqcleaner.utils.showToastBySystem

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

    //设定瘦身延迟的对话框
    fun showCleanDelayDialog(context: Context) {
        val input = EditText(context)
        input.setText(ConfigManager.getInt(CFG_CLEAN_DELAY, 24).toString())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.filters = arrayOf(InputFilter.LengthFilter(5))
        AlertDialog.Builder(context)
            .setView(input)
            .setTitle("设置瘦身延迟(小时)")
            .setPositiveButton("确定") { _, _ ->
                val num = input.text.toString().toInt()
                ConfigManager.setConfig(CFG_CLEAN_DELAY, if (num < 1) 24 else num)
                context.showToastBySystem("保存成功!")
            }
            .setNegativeButton("取消") { _, _ -> }
            .create()
            .show()
    }
}
