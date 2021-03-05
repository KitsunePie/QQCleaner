package me.kyuubiran.qqcleaner.utils


import me.kyuubiran.qqcleaner.data.hostApp
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_AUTO_CLEAN_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CLEAN_DELAY
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CURRENT_CLEANED_TIME
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CUSTOMER_CLEAN_MODE
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_TOTAL_CLEANED_SIZE
import me.kyuubiran.qqcleaner.utils.ConfigManager.getConfig
import me.kyuubiran.qqcleaner.utils.ConfigManager.getLong
import me.kyuubiran.qqcleaner.utils.clean.CleanQQ
import java.io.File
import kotlin.concurrent.thread

object CleanManager {
    //瘦身模式
    const val HALF_MODE = "half_mode"
    const val FULL_MODE = "full_mode"
    const val CUSTOMER_MODE = "customer_mode"

    //计算清理完毕后的释放的空间
    private var size = 0L

    //保存清理的内存
    private fun saveSize() {
        val totalSize = getLong(CFG_TOTAL_CLEANED_SIZE).plus(size)
        ConfigManager.setConfig(CFG_TOTAL_CLEANED_SIZE, totalSize)
    }

    /**
     * @param showToast 是否显示Toast
     * 一键瘦身
     */
    fun halfClean(showToast: Boolean = true) {
        when (hostApp) {
            HostApp.QQ -> doClean(CleanQQ.getHalfList(), showToast)
            HostApp.TIM -> {
            } //TODO
            HostApp.WE_CHAT -> {
            } //TODO
        }
    }

    /**
     * @param showToast 是否显示Toast
     * 彻底瘦身
     */
    fun fullClean(showToast: Boolean = true) {
        when (hostApp) {
            HostApp.QQ -> doClean(CleanQQ.getFullList(), showToast)
            HostApp.TIM -> {
            } //TODO
            HostApp.WE_CHAT -> {
            } //TODO
        }
    }

    /**
     * @param showToast 是否显示Toast
     * 自定义瘦身
     */
    fun customerClean(showToast: Boolean = true) {
        when (hostApp) {
            HostApp.QQ -> doClean(CleanQQ.getCustomerList(), showToast)
            HostApp.TIM -> {
            } //TODO
            HostApp.WE_CHAT -> {
            } //TODO
        }
    }

    /**
     * @param files 瘦身列表
     * @param showToast 是否显示toast
     * 执行瘦身的函数
     */
    private fun doClean(files: ArrayList<File>, showToast: Boolean = true) {
        thread {
            size = 0L
            if (showToast) appContext?.makeToast("好耶 开始清理了!")
            try {
                for (f in files) {
//                    appContext?.makeToast("开始清理${f.path}")
                    delFiles(f)
                }
                appContext?.makeToast("好耶 清理完毕了!腾出了${formatSize(size)}空间!")
                saveSize()
            } catch (e: Exception) {
                loge(e)
                appContext?.makeToast("坏耶 清理失败了!")
            }
        }
    }

    /**
     * @param file 文件/文件夹
     * 删除文件/文件夹的函数
     */
    private fun delFiles(file: File) {
        if (!file.exists()) return
        if (file.isFile) {
            size += file.length()
            file.delete()
        } else {
            val list = file.listFiles()
            if (list == null || list.isEmpty()) {
                file.delete()
            } else {
                for (f in list) {
                    delFiles(f)
                }
            }
        }
    }

    //自动瘦身的类
    class AutoClean {
        private var time = 0L
        private val delay = ConfigManager.getInt(CFG_CLEAN_DELAY, 24) * 3600L * 1000L
        private var mode = ""

        //在加载模块的时候会检测并执行一次
        init {
            time = getLong(CFG_CURRENT_CLEANED_TIME)
            //判断间隔
            if ((getConfig(CFG_AUTO_CLEAN_ENABLED)
                    ?: false) as Boolean && System.currentTimeMillis() - time > if (delay < 3600_000L) 24 * 3600L * 1000L else delay
            ) {
                mode = getConfig(CFG_CUSTOMER_CLEAN_MODE).toString()
                autoClean()
                time = System.currentTimeMillis()
                ConfigManager.setConfig(CFG_CURRENT_CLEANED_TIME, time)
            }
        }

        //自动瘦身
        private fun autoClean() {
            appContext?.makeToast("好耶 开始自动清理了!")
            when (mode) {
                FULL_MODE -> {
                    fullClean(false)
                }
                CUSTOMER_MODE -> {
                    customerClean(false)
                }
                else -> {
                    halfClean(false)
                }
            }
        }
    }
}