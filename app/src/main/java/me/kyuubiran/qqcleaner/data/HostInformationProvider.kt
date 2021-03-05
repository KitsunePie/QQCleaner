package me.kyuubiran.qqcleaner.data

import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.core.content.pm.PackageInfoCompat
import me.kyuubiran.qqcleaner.utils.HostApp
import me.kyuubiran.qqcleaner.utils.loge
import java.util.*

data class HostInformationProvider(
    val application: Application,
    val packageName: String,
    val hostName: String,
    val versionCode: Long,
    val versionCode32: Int,
    val versionName: String,
    val isTim: Boolean
)

lateinit var hostApp: HostApp
    private set

lateinit var hostInfo: HostInformationProvider
    private set

fun init(applicationContext: Application) {
    if (::hostInfo.isInitialized) throw IllegalStateException("Host Information Provider has been already initialized")
    val packageInfo = getHostInfo(applicationContext)
    val packageName = applicationContext.packageName
    hostInfo = HostInformationProvider(
        applicationContext,
        packageName,
        applicationContext.applicationInfo.loadLabel(applicationContext.packageManager).toString(),
        PackageInfoCompat.getLongVersionCode(packageInfo),
        PackageInfoCompat.getLongVersionCode(packageInfo).toInt(),
        packageInfo.versionName,
        "com.tencent.tim" == packageName
    )
    when (hostInfo.hostName.toLowerCase(Locale.ROOT)) {
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

private fun getHostInfo(context: Context): PackageInfo {
    return try {
        context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_META_DATA)
    } catch (e: Throwable) {
        loge("Utils--->Can not get PackageInfo!")
        throw AssertionError("Can not get PackageInfo!")
    }
}