package me.kyuubiran.qqcleaner.util

import com.github.kyuubiran.ezxhelper.utils.Log
import kotlin.concurrent.thread

object CleanManager {
    fun execute(showToast: Boolean) {
        thread {
            var cleanedSize = 0L
            if (showToast) Log.toast("正在执行清理...")
            try {

            } catch (e: Exception) {
                if (showToast) Log.toast("坏耶！清理失败了！")
                return@thread
            }
            ConfigManager.sTotalCleaned = ConfigManager.sTotalCleaned + cleanedSize
            if (showToast) Log.toast("好耶！清理完毕了！")
        }
    }
}