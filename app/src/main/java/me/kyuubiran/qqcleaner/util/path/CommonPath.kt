package me.kyuubiran.qqcleaner.util.path

import com.github.kyuubiran.ezxhelper.init.InitFields.appContext

object CommonPath {
    //P: storage/emulated/0/Android/data/${HostAppPackageName}
    val storageData by lazy {
        "!PublicDataDir" to (appContext.externalCacheDir?.parentFile?.path ?: "")
    }

    //P: data/user/0/${HostAppPackageName}
    val privateData by lazy {
        "!PrivateDataDir" to (appContext.filesDir?.parentFile?.path ?: "")
    }
}