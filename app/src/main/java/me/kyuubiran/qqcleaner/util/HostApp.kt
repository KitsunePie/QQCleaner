package me.kyuubiran.qqcleaner.util

enum class HostApp {
    QQ, TIM, WE_CHAT
}

lateinit var hostApp: HostApp

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