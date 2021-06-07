package me.kyuubiran.qqcleaner.util

enum class HostApp {
    QQ, TIM, WE_CHAT
}

lateinit var hostApp: HostApp


object HostAppUtil {
    fun isCurrentHostApp(appName: String): Boolean {
        return when (appName.lowercase()) {
            "qq" -> {
                hostApp == HostApp.QQ
            }
            "tim" -> {
                hostApp == HostApp.TIM
            }
            "we_chat" -> {
                hostApp == HostApp.WE_CHAT
            }
            else -> false
        }
    }

    fun containsCurrentHostApp(appName: String): Boolean {
        appName.lowercase().let {
            return when {
                it.contains("qq") -> {
                    hostApp == HostApp.QQ
                }
                it.contains("tim") -> {
                    hostApp == HostApp.TIM
                }
                it.contains("we_chat") -> {
                    hostApp == HostApp.WE_CHAT
                }
                else -> false
            }
        }
    }
}

val HostApp.isQq: Boolean
    get() = hostApp == HostApp.QQ

val HostApp.isTim: Boolean
    get() = hostApp == HostApp.TIM

val HostApp.isQqOrTim: Boolean
    get() = hostApp == HostApp.TIM || hostApp == HostApp.TIM

val HostApp.isWeChat: Boolean
    get() = hostApp == HostApp.WE_CHAT