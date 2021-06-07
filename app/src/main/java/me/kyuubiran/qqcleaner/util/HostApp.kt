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
    get() = hostApp == HostApp.TIM || hostApp == HostApp.TIM

val HostApp.isWeChat: Boolean
    get() = hostApp == HostApp.WE_CHAT

lateinit var hostApp: HostApp

val hostAppName: String
    get() {
        return when (hostApp) {
            HostApp.QQ -> "QQ"
            HostApp.TIM -> "TIM"
            HostApp.WE_CHAT -> "微信"
        }
    }


object HostAppUtil {
    const val HOST_QQ = "qq"
    const val HOST_TIM = "tim"
    const val HOST_WE_CHAT = "we_chat"

    fun isCurrentHostApp(appName: String): Boolean {
        return when (appName.lowercase()) {
            HOST_QQ -> {
                hostApp == HostApp.QQ
            }
            HOST_TIM -> {
                hostApp == HostApp.TIM
            }
            HOST_WE_CHAT -> {
                hostApp == HostApp.WE_CHAT
            }
            else -> false
        }
    }

    fun isCurrentHostAppByPackageName(packageName: String): Boolean {
        return packageName == InitFields.hostPackageName
    }

    fun containsCurrentHostApp(appName: String): Boolean {
        appName.lowercase().let {
            return when {
                it.contains(HOST_QQ) -> {
                    hostApp == HostApp.QQ
                }
                it.contains(HOST_TIM) -> {
                    hostApp == HostApp.TIM
                }
                it.contains(HOST_WE_CHAT) -> {
                    hostApp == HostApp.WE_CHAT
                }
                else -> false
            }
        }
    }
}

