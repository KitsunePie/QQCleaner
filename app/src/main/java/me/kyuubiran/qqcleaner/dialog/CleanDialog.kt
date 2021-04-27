package me.kyuubiran.qqcleaner.dialog

import android.app.AlertDialog
import android.content.Context
import android.preference.Preference
import android.preference.SwitchPreference
import android.text.InputFilter
import android.text.InputType
import android.widget.EditText
import me.kyuubiran.qqcleaner.utils.CleanManager
import me.kyuubiran.qqcleaner.utils.ConfigManager
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CLEAN_DELAY
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_DATE_LIMIT
import me.kyuubiran.qqcleaner.utils.showToastX

const val HALF_MODE_INT = 0
const val FULL_MODE_INT = 1
const val CUSTOMER_MODE_INT = 2

object CleanDialog {
    private fun getMessage(mode: Int): String {
        return when (mode) {
            HALF_MODE_INT -> "一键瘦身"
            FULL_MODE_INT -> "完全瘦身"
            CUSTOMER_MODE_INT -> "自定义瘦身"
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
                    HALF_MODE_INT -> CleanManager.halfClean()
                    FULL_MODE_INT -> CleanManager.fullClean()
                    CUSTOMER_MODE_INT -> CleanManager.customerClean()
                }
            }
            .create()
            .show()
    }

    //设定瘦身延迟的对话框
    fun showCleanDelayDialog(context: Context, spc: SwitchPreference) {
        val input = EditText(context)
        input.setText(ConfigManager.getInt(CFG_CLEAN_DELAY, 24).toString())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.filters = arrayOf(InputFilter.LengthFilter(5))
        AlertDialog.Builder(context)
            .setView(input)
            .setTitle("设置瘦身延迟(小时)")
            .setPositiveButton("确定") { _, _ ->
                var num = input.text.toString().toInt()
                num = if (num < 1) 24 else num
                ConfigManager.setConfig(CFG_CLEAN_DELAY, num)
                context.showToastX("好耶 保存成功了!")
                spc.summary = "当前清理的间隔为${num}小时"
            }
            .setNegativeButton("取消") { _, _ -> }
            .create()
            .show()
    }

    fun showSetFileDateLimitDialog(context: Context, pf: Preference) {
        val input = EditText(context)
        input.setText(ConfigManager.getInt(CFG_DATE_LIMIT, 3).toString())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.filters = arrayOf(InputFilter.LengthFilter(5))
        AlertDialog.Builder(context)
            .setView(input)
            .setTitle("设置过期文件时间(天)")
            .setPositiveButton("确定") { _, _ ->
                var num = input.text.toString().toInt()
                num = if (num < 1) 3 else num
                ConfigManager.setConfig(CFG_DATE_LIMIT, num)
                context.showToastX("好耶 保存成功了!")
                pf.summary = "当前会清理存在超过${num}天的文件"
            }
            .setNegativeButton("取消") { _, _ -> }
            .create()
            .show()
    }
}
