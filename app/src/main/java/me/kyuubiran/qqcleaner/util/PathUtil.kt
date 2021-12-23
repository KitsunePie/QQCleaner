package me.kyuubiran.qqcleaner.util

import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.util.path.CommonPath
import me.kyuubiran.qqcleaner.util.path.QQPath

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
        }
        return tmp
    }
}