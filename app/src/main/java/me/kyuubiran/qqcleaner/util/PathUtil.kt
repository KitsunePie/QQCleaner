package me.kyuubiran.qqcleaner.util

import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.util.path.CommonPath
import me.kyuubiran.qqcleaner.util.path.QQPath
import me.kyuubiran.qqcleaner.util.path.WeChatPath

object PathUtil {
    // -CommonPath
    const val STORAGE_ANDROID_DATA = "!StorageDataDir"
    const val DATA_ANDROID_DATA = "!PrivateDataDir"

    // -QQ/TIM
    const val QQ_STORAGE_TENCENT = "!TencentDir"

    // -WeChat
    const val WE_CHAT_STORAGE_USER_DATA = "!StorageUserData"
    const val WE_CHAT_DATA_USER_DATA = "!PrivateUserData"

    fun getFullPath(path: String): String {
        var tmp = path
        when {
            tmp.startsWith(WE_CHAT_STORAGE_USER_DATA) -> {
                tmp = tmp.replaceFirst(STORAGE_ANDROID_DATA, CommonPath.sAndroidDataDir)
            }
            tmp.startsWith(DATA_ANDROID_DATA) -> {
                tmp = tmp.replaceFirst(DATA_ANDROID_DATA, CommonPath.dDataDir)
            }
        }
        when {
            hostApp.isQqOrTim && tmp.startsWith(QQ_STORAGE_TENCENT) -> {
                tmp = tmp.replaceFirst(QQ_STORAGE_TENCENT, QQPath.sTencentDir)
            }
            hostApp.isWeChat && tmp.startsWith(WE_CHAT_STORAGE_USER_DATA) -> {
                WeChatPath.sUserData.let {
                    if (it != null) {
                        tmp = tmp.replaceFirst(WE_CHAT_STORAGE_USER_DATA, it)
                    } else {
                        Log.w("Get WE_CHAT_STORAGE_USER_DATA failed")
                    }
                }
            }
            hostApp.isWeChat && tmp.startsWith(WE_CHAT_DATA_USER_DATA) -> {
                WeChatPath.sUserData.let {
                    if (it != null) {
                        tmp = tmp.replaceFirst(WE_CHAT_DATA_USER_DATA, it)
                    } else {
                        Log.w("Get WE_CHAT_DATA_USER_DATA failed")
                    }
                }
            }
        }
        return tmp
    }
}