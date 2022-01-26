package me.kyuubiran.qqcleaner.util

import com.github.kyuubiran.ezxhelper.init.InitFields

enum class HostApp {
    QQ, TIM, WE_CHAT
}

val HostApp.isQq: Boolean
    get() = hostApp == HostApp.QQ

val HostApp.isTim: Boolean
    get() = hostApp == HostApp.TIM

val HostApp.isQqOrTim: Boolean
    get() = hostApp == HostApp.QQ || hostApp == HostApp.TIM

val HostApp.isWeChat: Boolean
    get() = hostApp == HostApp.WE_CHAT

lateinit var hostApp: HostApp

val hostAppName: String
    get() = when (hostApp) {
        HostApp.QQ -> "QQ"
        HostApp.TIM -> "TIM"
        HostApp.WE_CHAT -> "WECHAT"
    }


object HostAppUtil {
    fun isCurrentHostAppByPackageName(packageName: String): Boolean {
        return packageName == InitFields.hostPackageName
    }

    fun containsCurrentHostApp(appName: String): Boolean {
        return appName.contains(hostAppName, ignoreCase = true)
    }
}

