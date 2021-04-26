package me.kyuubiran.qqcleaner.utils

import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import kotlin.concurrent.thread

object ConfigManager {
    private val config = File("${appContext.filesDir.absolutePath}/qqcleaner.json")

    const val CFG_AUTO_CLEAN_ENABLED = "autoCleanEnabled"
    const val CFG_AUTO_CLEAN_MODE = "autoCleanMode"
    const val CFG_CURRENT_CLEANED_TIME = "cleanedTime"
    const val CFG_CUSTOMER_CLEAN_LIST = "customerList"
    const val CFG_TOTAL_CLEANED_SIZE = "totalCleanedSize"
    const val CFG_CLEAN_DELAY = "cleanDelay"
    const val CFG_POWER_MODE_ENABLED = "powerModeEnabled"
    const val CFG_DATE_LIMIT_ENABLED = "dateLimitEnabled"
    const val CFG_DO_NOT_DISTURB_ENABLED = "doNotDisturbEnabled"
    const val CFG_DATE_LIMIT = "dateLimit"

    fun checkConfigIsExists() {
        if (!config.exists()) {
            config.createNewFile()
            save("{}")
        }
    }

    fun checkCfg() {
        checkConfigIsExists()
        checkKeyHasValue(CFG_CURRENT_CLEANED_TIME, 0)
        checkKeyHasValue(CFG_TOTAL_CLEANED_SIZE, 0)
        checkArrayHasValue(CFG_CUSTOMER_CLEAN_LIST, ArrayList<String>())
    }

    private fun checkKeyHasValue(key: String, defValue: Any): Boolean {
        return if (getObject(key) == null || getObject(key).toString() == "null") {
            setConfig(key, defValue)
            false
        } else true
    }

    private fun <T> checkArrayHasValue(key: String, defValue: ArrayList<T>) {
        setArray(key, defValue)
    }

    private fun getConfig(): JSONObject? {
        checkConfigIsExists()
        return try {
            val cfg = config.readText(Charsets.UTF_8)
            JSONObject(if (cfg.isNotEmpty()) cfg else "{}")
        } catch (e: Exception) {
            Log.e(e)
            config.delete()
            checkCfg()
            JSONObject("{}")
        }
    }

    fun getObject(key: String): Any? {
        return try {
            getConfig()?.get(key)
        } catch (e: Exception) {
            null
        }
    }


    fun getLong(key: String, defValue: Long = 0L): Long {
        return try {
            getConfig()?.getLong(key) ?: defValue
        } catch (e: Exception) {
            defValue
        }
    }

    fun getInt(key: String, defValue: Int = 0): Int {
        return try {
            getConfig()?.getInt(key) ?: defValue
        } catch (e: Exception) {
            defValue
        }
    }

    fun getBool(key: String, defValue: Boolean = false): Boolean {
        return try {
            getConfig()?.getBoolean(key) ?: defValue
        } catch (e: Exception) {
            defValue
        }
    }

    fun getString(key: String, defValue: String = ""): String {
        return try {
            getConfig()?.getString(key) ?: defValue
        } catch (e: Exception) {
            defValue
        }
    }

    fun <T> setConfig(key: String, value: T) {
        try {
            val config = getConfig()
            config?.let {
                it.put(key, value)
                save(config)
            }
        } catch (e: Exception) {
            Log.e(e)
        }
    }

    private fun save(str: String) {
        thread {
            try {
                synchronized(this) {
                    val osw = OutputStreamWriter(FileOutputStream(config), Charsets.UTF_8)
                    osw.write(str)
                    osw.flush()
                    osw.close()
                }
            } catch (e: Exception) {
                Log.e(e)
            }
        }
    }

    fun <T> setArray(key: String, arr: ArrayList<T>) {
        val jsonArray = JSONArray()
        for (v in arr) {
            jsonArray.put(v)
        }
        setConfig(key, jsonArray)
    }

    fun <T> setArray(key: String, hs: HashSet<T>) {
        val jsonArray = JSONArray()
        for (v in hs) {
            jsonArray.put(v)
        }
        setConfig(key, jsonArray)
    }

    fun <T> setArray(key: String, arr: Array<T>) {
        val jsonArray = JSONArray()
        for (v in arr) {
            jsonArray.put(v)
        }
        setConfig(key, jsonArray)
    }

    fun getArray(key: String): JSONArray? {
        return getConfig()?.getJSONArray(key)
    }

    private fun save(jsonObject: JSONObject) {
        save(jsonObject.toString())
    }
}
