package me.kyuubiran.qqcleaner.util

import me.kyuubiran.qqcleaner.util.path.CommonPath
import me.kyuubiran.qqcleaner.util.path.QQPath

object PathUtil {
    private fun replacePrefix(path: String, kv: Pair<String, String>): String {
        return if (path.startsWith(kv.first)) path.replaceFirst(kv.first, kv.second) else path
    }

    fun getFullPath(path: String): String {
        var tmp = path
        tmp = replacePrefix(tmp, CommonPath.storageData)
        tmp = replacePrefix(tmp, CommonPath.privateData)
        when {
            hostApp.isQqOrTim -> {
                tmp = replacePrefix(tmp, QQPath.tencentDir)
            }
            hostApp.isWeChat -> {
            }
        }
        return tmp
    }
}