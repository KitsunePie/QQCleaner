package me.kyuubiran.qqcleaner.util

import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.util.path.CommonPath
import java.io.File
import kotlin.concurrent.thread

object CleanManager {
    fun execute(showToast: Boolean) {
        thread {
            if (showToast) Log.toast("正在执行清理...")
            try {
                getAllConfigs().forEach {
                    executeCleanData(it)
                }
            } catch (e: Exception) {
                if (showToast) Log.toast("坏耶！清理失败了！")
                return@thread
            }
            if (showToast) Log.toast("好耶！清理完毕了！")
        }
    }

    private fun deleteAll(path: String) {
        deleteAll(f = File(path))
    }

    private fun deleteAll(f: File) {
        thread {
            try {
                if (!f.exists()) return@thread
                if (f.isFile) {
                    f.delete()
                } else {
                    f.listFiles().let { arr ->
                        arr?.forEach {
                            deleteAll(it)
                        } ?: f.delete()
                    }
                }
            } catch (e: Exception) {
                Log.e(e)
            }
        }
    }

    fun executeCleanData(cleanData: CleanData) {
        if (!cleanData.enable || !cleanData.valid) return
        cleanData.content.forEach { data ->
            if (!data.enable) return@forEach
            data.pathList.forEach { path ->
                deleteAll(path)
            }
        }
    }

    fun getConfigDir(): File {
        val path = "${CommonPath.sAndroidDataDir}/qqcleaner"
        val f = File(path)
        if (f.exists()) return f
        f.mkdir()
        return f
    }

    fun getAllConfigs(): Array<CleanData> {
        val arr = ArrayList<CleanData>()
        try {
            getConfigDir().listFiles()?.let {
                it.forEach { f ->
                    try {
                        arr.add(CleanData(f))
                    } catch (e: Exception) {
                        Log.e(e)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(e)
        }
        return arr.toTypedArray()
    }
}