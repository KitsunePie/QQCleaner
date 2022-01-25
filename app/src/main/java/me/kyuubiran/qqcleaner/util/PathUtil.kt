package me.kyuubiran.qqcleaner.util

import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.util.path.CommonPath
import me.kyuubiran.qqcleaner.util.path.QQPath
import me.kyuubiran.qqcleaner.util.path.WeChatPath

object PathUtil {
    fun getFullPath(path: CleanData.PathData.Path): String {
        var tmp = path.suffix

        when (path.prefix) {
            CommonPath.publicData.first ->
                tmp = CommonPath.publicData.second + path.suffix
            CommonPath.privateData.first ->
                tmp = CommonPath.privateData.second + path.suffix
            QQPath.tencentDir.first ->
                tmp = QQPath.tencentDir.second + path.suffix
            WeChatPath.publicUserData.first ->
                tmp = WeChatPath.publicUserData.second + path.suffix
            WeChatPath.privateUserData.first ->
                tmp = WeChatPath.privateUserData.second + path.suffix
        }
        return tmp
    }
}