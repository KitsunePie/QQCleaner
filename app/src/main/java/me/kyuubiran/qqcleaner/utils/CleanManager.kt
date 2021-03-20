package me.kyuubiran.qqcleaner.utils


import com.alibaba.fastjson.JSONArray
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.runtimeProcess
import com.github.kyuubiran.ezxhelper.utils.showToast
import me.kyuubiran.qqcleaner.data.hostApp
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_AUTO_CLEAN_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CLEAN_DELAY
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CURRENT_CLEANED_TIME
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CUSTOMER_CLEAN_MODE
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_DATE_LIMIT
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_DATE_LIMIT_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_POWER_MODE_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_TOTAL_CLEANED_SIZE
import me.kyuubiran.qqcleaner.utils.ConfigManager.getBool
import me.kyuubiran.qqcleaner.utils.ConfigManager.getConfig
import me.kyuubiran.qqcleaner.utils.ConfigManager.getInt
import me.kyuubiran.qqcleaner.utils.ConfigManager.getLong
import me.kyuubiran.qqcleaner.utils.clean.CleanQQ
import me.kyuubiran.qqcleaner.utils.clean.CleanWeChat
import java.io.File
import kotlin.concurrent.thread

object CleanManager {
    //瘦身模式
    const val HALF_MODE = "half_mode"
    const val FULL_MODE = "full_mode"
    const val CUSTOMER_MODE = "customer_mode"

    private val isCleanedByShell = getBool(CFG_POWER_MODE_ENABLED)

    //计算清理完毕后的释放的空间
    private var size = 0L

    //保存清理的内存
    private fun saveSize() {
        val totalSize = getLong(CFG_TOTAL_CLEANED_SIZE).plus(size)
        ConfigManager.setConfig(CFG_TOTAL_CLEANED_SIZE, totalSize)
    }

    /**
     * @return 获取用户自定义的瘦身列表
     */
    private fun getCustomerList(): ArrayList<File> {
        val customerList = getConfig(ConfigManager.CFG_CUSTOMER_CLEAN_LIST) as JSONArray
        val arr = ArrayList<File>()
        for (s in customerList) {
            when (hostApp) {
                HostApp.QQ -> arr.addAll(CleanQQ.getFiles(s.toString()))
                HostApp.TIM -> arr.addAll(CleanQQ.getFiles(s.toString()))
                HostApp.WE_CHAT -> arr.addAll(CleanWeChat.getFiles(s.toString()))
            }
        }
        return arr
    }

    /**
     * @param showToast 是否显示Toast
     * 一键瘦身
     */
    fun halfClean(showToast: Boolean = true) {
        when (hostApp) {
            HostApp.QQ -> doClean(CleanQQ.getHalfList(), showToast)
            HostApp.TIM -> doClean(CleanQQ.getHalfList(), showToast)
            HostApp.WE_CHAT -> doClean(CleanWeChat.getHalfList(), showToast)
        }
    }

    /**
     * @param showToast 是否显示Toast
     * 彻底瘦身
     */
    fun fullClean(showToast: Boolean = true) {
        when (hostApp) {
            HostApp.QQ -> doClean(CleanQQ.getFullList(), showToast)
            HostApp.TIM -> doClean(CleanQQ.getFullList(), showToast)
            HostApp.WE_CHAT -> doClean(CleanWeChat.getFullList(), showToast)
        }
    }

    /**
     * @param showToast 是否显示Toast
     * 自定义瘦身
     */
    fun customerClean(showToast: Boolean = true) {
        doClean(getCustomerList(), showToast)
    }

    /**
     * @param files 瘦身列表
     * @param showToast 是否显示toast
     * 执行瘦身的函数
     */
    private fun doClean(files: ArrayList<File>, showToast: Boolean = true) {
        thread {
            size = 0L
            if (showToast) appContext.showToast("好耶 开始清理了!")
            try {
                val ofd = getInt(CFG_DATE_LIMIT, 3)
                val ts = System.currentTimeMillis()
                val lmtEnable = getBool(CFG_DATE_LIMIT_ENABLED)
                for (f in files) {
//                    logi("开始清理${f.path}")
                    delAllFiles(f, lmtEnable, ofd, ts)
                }
                appContext.showToast("好耶 清理完毕了!腾出了${formatSize(size)}空间!")
                saveSize()
            } catch (e: Exception) {
                Log.e(e)
                appContext.showToast("坏耶 清理失败了!")
            }
        }
    }

    private fun File.deleteSingleByShell() {
        if (this.exists() && this.isFile) {
            runtimeProcess.exec("rm -f ${this.path}")
        }
    }

    /**
     * @param file 文件/文件夹
     * @param limitEnable 是否开启天数过滤
     * @param outOfDate 文件过期时间(天)
     * @param ts 当前时间戳
     * 删除文件/文件夹的函数
     */
    private fun delAllFiles(
        file: File,
        limitEnable: Boolean = false,
        outOfDate: Int = 3,
        ts: Long = System.currentTimeMillis()
    ) {
        if (!file.exists()) return
        //清理用
        fun delete(f: File) {
            size += f.length()
            if (isCleanedByShell) {
                f.deleteSingleByShell()
            } else {
                f.delete()
            }
        }
        //文件
        if (file.isFile) {
            //如果需要过滤文件
            if (limitEnable) {
                val ofdTime = outOfDate * 24L * 60L * 60L * 1000L
                if (ts - file.lastModified() > ofdTime) {
                    delete(file)
                }
            } else {
                //不需要直接删
                delete(file)
            }
        } else {
            //文件夹
            val list = file.listFiles()
            if (list == null || list.isEmpty()) {
                file.delete()
            } else {
                for (f in list) {
                    delAllFiles(f, limitEnable, outOfDate, ts)
                }
            }
        }
    }

    class AutoClean {
        private var time = 0L
        private val delay = ConfigManager.getInt(CFG_CLEAN_DELAY, 24) * 3600L * 1000L
        private var mode = ""

        //在加载模块的时候会检测并执行一次
        init {
            time = getLong(CFG_CURRENT_CLEANED_TIME)
            //判断间隔
            if (getBool(CFG_AUTO_CLEAN_ENABLED) && System.currentTimeMillis() - time > if (delay < 3600_000L) 24 * 3600L * 1000L else delay
            ) {
                mode = getConfig(CFG_CUSTOMER_CLEAN_MODE).toString()
                autoClean()
                time = System.currentTimeMillis()
                ConfigManager.setConfig(CFG_CURRENT_CLEANED_TIME, time)
            }
        }

        //自动瘦身
        private fun autoClean() {
            appContext.showToast("好耶 开始自动清理了!")
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