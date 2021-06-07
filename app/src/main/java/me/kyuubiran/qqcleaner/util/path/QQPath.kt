package me.kyuubiran.qqcleaner.util.path

import com.github.kyuubiran.ezxhelper.init.InitFields

object QQPath {
    //P: storage/emulated/0/tencent
    val sTencentDir by lazy {
        "${InitFields.appContext.obbDir?.parentFile?.parentFile?.parentFile?.path}/tencent"
    }
}