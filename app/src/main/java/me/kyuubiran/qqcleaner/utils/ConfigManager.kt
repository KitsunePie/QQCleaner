package me.kyuubiran.qqcleaner.utils

import com.alibaba.fastjson.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.lang.Exception
import kotlin.concurrent.thread

object ConfigManager {
    private val config = File("${qqContext?.filesDir?.absolutePath}/qqcleaner.json")

    const val CFG_AUTO_CLEAN_ENABLED = "autoCleanEnabled"
    const val CFG_CURRENT_CLEANED_TIME = "cleanedTime"
    const val CFG_CUSTOMER_CLEAN_LIST = "customerList"
    const val CFG_CUSTOMER_CLEAN_MODE = "customerCleanMode"
    const val CFG_TOTAL_CLEANED_SIZE = "totalCleanedSize"

    fun checkConfigIsExists() {
        if (!config.exists()) {
            config.createNewFile()
            save("{}")
        }
    }

    fun checkCfg() {
        checkConfigIsExists()
        checkConfigKeyHasValue(CFG_CURRENT_CLEANED_TIME, 0)
        checkConfigKeyHasValue(CFG_TOTAL_CLEANED_SIZE, 0)
        checkConfigKeyHasValue(CFG_CUSTOMER_CLEAN_LIST, ArrayList<String>())
    }

    private fun checkConfigKeyHasValue(key: String, defValue: Any): Boolean {
        return if (getConfig(key) == null || getConfig(key).toString() == "null") {
            setConfig(key, defValue)
            false
        } else true
    }

    private fun getConfig(): JSONObject? {
        checkConfigIsExists()
        return try {
            JSONObject.parseObject(config.readText(Charsets.UTF_8))
        } catch (e: Exception) {
            loge(e)
            config.delete()
            checkCfg()
            JSONObject.parseObject(config.readText(Charsets.UTF_8))
        }
    }

    fun getConfig(key: String): Any? {
        return getConfig()?.get(key)
    }

    fun getLong(key: String): Long? {
        return getConfig()?.getLong(key)
    }

    fun <T> setConfig(key: String, value: T) {
        try {
            val config = getConfig()
            config?.set(key, value)
            if (config != null) {
                save(config)
            }
        } catch (e: Exception) {
            loge(e)
        }
    }

    private fun save(str: String) {
        thread {
            try {
                val osw = OutputStreamWriter(FileOutputStream(config), Charsets.UTF_8)
                osw.write(str)
                osw.flush()
                osw.close()
            } catch (e: Exception) {
                loge(e)
            }
        }
    }

    private fun save(jsonObject: JSONObject) {
        save(jsonObject.toJSONString())
    }
}