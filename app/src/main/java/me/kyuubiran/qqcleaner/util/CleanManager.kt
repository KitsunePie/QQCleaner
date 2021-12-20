package me.kyuubiran.qqcleaner.util

import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.util.path.CommonPath
import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

object CleanManager {
    private val pool = ThreadPoolExecutor(1, 1, 180L, TimeUnit.SECONDS, LinkedBlockingQueue(256))

    fun CleanData.execute(showToast: Boolean = true) {
        if (!this.enable || !this.valid) return
        if (showToast) Log.toast(
            appContext.getString(R.string.executing_config).format(this.title)
        )
        pool.execute e@{
            try {
                this.content.forEach { data ->
                    if (!data.enable) return@forEach
                    data.pathList.forEach { path ->
                        deleteAll(PathUtil.getFullPath(path))
                    }
                }
            } catch (e: Exception) {
                if (showToast) Log.toast(
                    appContext.getString(R.string.clean_failed).format(this.title)
                )
            }
        }
    }

    fun executeAll(showToast: Boolean = true) {
        if (showToast) Log.toast(appContext.getString(R.string.clean_start))
        getAllConfigs().forEach {
            it.execute(showToast)
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

    fun getConfigDir(): File {
        val path = "${CommonPath.storageData}/qqcleaner"
        val f = File(path)
        if (f.exists()) return f
        f.mkdir()
        return f
    }

    private fun getAllConfigs(): Array<CleanData> {
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