package me.kyuubiran.qqcleaner.util

import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.mainHandler

object AutoCleanManager : Runnable {
    val initAutoClean by lazy {
        Log.i("Init auto clean")
        mainHandler.postDelayed(this, 1000 * 30)
    }

    private fun needClean(): Boolean {
        return System.currentTimeMillis() - ConfigManager.sLastCleanDate > (ConfigManager.sAutoCleanInterval * 60 * 60)
    }

    override fun run() {
        if (!ConfigManager.sAutoClean || !needClean()) return
        ConfigManager.sLastCleanDate = System.currentTimeMillis()
        CleanManager.executeAll(!ConfigManager.sSilenceClean)
        mainHandler.postDelayed(this, 1000 * 60 * 10)
    }
}