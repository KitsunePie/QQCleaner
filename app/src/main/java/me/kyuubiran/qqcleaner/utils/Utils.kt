package me.kyuubiran.qqcleaner.utils

import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.loadClass
import me.kyuubiran.qqcleaner.data.hostApp
import java.math.BigDecimal

fun getAppRuntime(): Any? {
    return try {
        val mAppRuntime = Class.forName("mqq.app.MobileQQ").getDeclaredField("mAppRuntime")
        mAppRuntime.isAccessible = true
        mAppRuntime.get(appContext)
    } catch (e: Exception) {
        Log.e(e)
        null
    }
}

fun isInNightMode(): Boolean {
    return try {
        when (hostApp) {
            HostApp.TIM, HostApp.WE_CHAT -> {
                false
            }
            HostApp.QQ -> {
                val themeId = loadClass("com.tencent.mobileqq.theme.ThemeUtil")
                    .getDeclaredMethod("getUserCurrentThemeId", loadClass("mqq.app.AppRuntime"))
                    .invoke(null, getAppRuntime()) as String
                "1103".endsWith(themeId) || "2920".endsWith(themeId)
            }
        }
    } catch (e: Exception) {
        Log.e(e)
        false
    }
}

//格式化清理空间
fun formatSize(size: Long): String {
    return formatSize(size.toString())
}

//格式化清理空间
fun formatSize(size: String): String {
    val sl = BigDecimal(size)
    val b: BigDecimal
    val result: Double
    val len = size.length
    return when {
        len in 0..3 -> {
            " $sl Byte "
        }
        len in 4..6 -> {
            b = sl.divide(BigDecimal(1_024.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            " $result KB "
        }
        len in 7..9 -> {
            b = sl.divide(BigDecimal(1_048_576.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            " $result MB "
        }
        len in 10..12 -> {
            b = sl.divide(BigDecimal(1_073_741_824.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            " $result GB "
        }
        len > 12 -> {
            b = sl.divide(BigDecimal(1_099_511_627_776.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            " $result TB "
        }
        else -> {
            ""
        }
    }
}