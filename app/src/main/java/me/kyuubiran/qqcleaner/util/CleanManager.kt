package me.kyuubiran.qqcleaner.util

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logeIfThrow
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.util.path.CommonPath
import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

object CleanManager {
    private val pool = ThreadPoolExecutor(1, 1, 5L, TimeUnit.MINUTES, LinkedBlockingQueue(256))

    fun execute(data: CleanData, showToast: Boolean = true, forceExec: Boolean = false) {
        if (!data.valid) return
        if (!data.enable && !forceExec) return
        if (showToast) Log.toast(
            moduleRes.getString(R.string.executing_config).format(data.title)
        )
        pool.execute e@{
            runCatching {
                data.content.forEach { data ->
                    if (!data.enable) return@forEach
                    data.pathList.forEach { path ->
                        deleteAll(PathUtil.getFullPath(path))
                    }
                }
            }.logeIfThrow("Execute failed, skipped: ${data.title}") {
                if (showToast) Log.toast(
                    moduleRes.getString(R.string.clean_failed).format(data.title)
                )
            }
        }
    }

    fun executeAll(showToast: Boolean = true) {
        if (showToast) Log.toast(moduleRes.getString(R.string.clean_start))
        getAllConfigsAsync {
            if (it.isEmpty() || it.all { c -> !c.enable }) {
                Log.toast(moduleRes.getString(R.string.no_config_enabled))
                return@getAllConfigsAsync
            }
            it.forEach { data ->
                execute(data, showToast)
            }
        }
    }

    private fun deleteAll(path: String) {
        deleteAll(f = File(path))
    }

    private fun deleteAll(f: File) {
        runCatching {
            if (!f.exists()) return
            if (f.isFile) {
                f.delete()
            } else {
                f.listFiles().let { arr ->
                    arr?.forEach {
                        deleteAll(it)
                    } ?: f.delete()
                }
            }
        }.logeIfThrow()
    }

    fun getConfigDir(): File {
        val path = "${CommonPath.publicData.second}/qqcleaner"
        val f = File(path)
        if (f.exists()) return f
        f.mkdir()
        return f
    }

    fun getAllConfigsAsync(onFinish: (Array<CleanData>) -> Unit) {
        thread {
            val arr = getAllConfigs()
            onFinish(arr)
        }
    }

    fun getAllConfigs(): Array<CleanData> {
        val arr = ArrayList<CleanData>()
        runCatching {
            getConfigDir().listFiles()?.let {
                it.forEach { f ->
                    runCatching {
                        arr.add(CleanData(f))
                    }.logeIfThrow()
                }
            }
        }.logeIfThrow()
        return arr.toTypedArray()
    }

    fun isConfigEmpty(): Boolean {
        return getConfigDir().listFiles()?.isEmpty() ?: true
    }
}
