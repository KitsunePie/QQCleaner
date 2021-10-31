package me.kyuubiran.qqcleaner.data

import android.app.Application
import me.kyuubiran.qqcleaner.utils.HostApp
import java.util.Locale

data class HostInformationProvider(
    val application: Application,
    val packageName: String,
    val hostName: String
)

lateinit var hostApp: HostApp
    private set

lateinit var hostInfo: HostInformationProvider
    private set

fun init(applicationContext: Application) {
    if (::hostInfo.isInitialized) throw IllegalStateException("Host Information Provider has been already initialized")
    val packageName = applicationContext.packageName
    hostInfo = HostInformationProvider(
        applicationContext,
        packageName,
        applicationContext.applicationInfo.loadLabel(applicationContext.packageManager).toString()
    )
    when (hostInfo.hostName.lowercase(Locale.ROOT)) {
        "qq" -> {
            hostApp = HostApp.QQ
        }
        "tim" -> {
            hostApp = HostApp.TIM
        }
        "微信", "wechat" -> {
            hostApp = HostApp.WE_CHAT
        }
    }
}
