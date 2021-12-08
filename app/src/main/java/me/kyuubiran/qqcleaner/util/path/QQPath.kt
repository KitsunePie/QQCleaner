package me.kyuubiran.qqcleaner.util.path

import com.github.kyuubiran.ezxhelper.init.InitFields.appContext

object QQPath {
    //P: storage/emulated/0/tencent
    val tencentDir by lazy {
        "!TencentDir" to (appContext.obbDir?.parentFile?.parentFile?.parentFile?.path?.let { "${it}/tencent" }
            ?: "")
    }
}