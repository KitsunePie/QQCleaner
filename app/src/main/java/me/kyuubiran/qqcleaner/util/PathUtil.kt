package me.kyuubiran.qqcleaner.util

import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.util.path.CommonPath
import me.kyuubiran.qqcleaner.util.path.QQPath
import me.kyuubiran.qqcleaner.util.path.WeChatPath

object PathUtil {
    /**
     * @throws IllegalArgumentException if [path] is not a valid path
     */
    fun getFullPath(path: CleanData.PathData.Path): String {
        var tmp = path.suffix

        when (path.prefix) {
            CommonPath.publicData.first ->
                tmp = CommonPath.publicData.second + path.suffix
            CommonPath.privateData.first ->
                tmp = CommonPath.privateData.second + path.suffix
            QQPath.tencentDir.first ->
                if (hostApp.isQqOrTim) tmp = QQPath.tencentDir.second + path.suffix
            WeChatPath.publicUserData.first ->
                if (hostApp.isWeChat) tmp = WeChatPath.publicUserData.second + path.suffix
            WeChatPath.privateUserData.first ->
                if (hostApp.isWeChat) tmp = WeChatPath.privateUserData.second + path.suffix
        }

        if (tmp == path.suffix) throw IllegalArgumentException("Unsupported path prefix: ${path.prefix}")
        return tmp
    }
}