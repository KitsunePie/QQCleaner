package me.kyuubiran.qqcleaner.util

import android.content.Context
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext

object ConfigManager {
    val sPrefs by lazy {
        appContext.getSharedPreferences("qqcleaner", Context.MODE_PRIVATE)
    }

    fun putBool(key: String, value: Boolean) {
        sPrefs.edit().putBoolean(key, value).apply()
    }

    fun getBool(key: String, defValue: Boolean = false): Boolean {
        return sPrefs.getBoolean(key, defValue)
    }

    fun putString(key: String, value: String) {
        sPrefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String = ""): String {
        return sPrefs.getString(key, defValue) ?: defValue
    }

    fun putStringSet(key: String, value: Set<String>) {
        sPrefs.edit().putStringSet(key, value).apply()
    }

    fun getStringSet(key: String, defValue: Set<String> = emptySet()): Set<String> {
        return sPrefs.getStringSet(key, defValue) ?: emptySet()
    }

    fun putInt(key: String, value: Int) {
        sPrefs.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defValue: Int = 0): Int {
        return sPrefs.getInt(key, defValue)
    }

    fun putFloat(key: String, value: Float) {
        sPrefs.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String, defValue: Float = 0.0f): Float {
        return sPrefs.getFloat(key, defValue)
    }

    fun putLong(key: String, value: Long) {
        sPrefs.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defValue: Long = 0L): Long {
        return sPrefs.getLong(key, defValue)
    }

    var sAutoClean: Boolean
        set(value) = putBool("auto_clean", value)
        get() = getBool("auto_clean")

    var sAutoCleanInterval: Int
        set(value) = putInt("auto_clean_interval", value)
        get() = getInt("auto_clean_interval", 24)

    var sLastCleanDate
        set(value) = putLong("last_clean_date", value)
        get() = getLong("last_clean_date")

    var sTotalCleaned: Long
        set(value) = putLong("total_cleaned", value)
        get() = getLong("total_cleaned")

    var sSilenceClean: Boolean
        set(value) = putBool("silence_clean", value)
        get() = getBool("silence_clean")

    var sThemeSelect: Int
        set(value) = putInt("theme_select", value)
        get() = getInt("theme_select")
}