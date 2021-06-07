package me.kyuubiran.qqcleaner.util.path

import com.github.kyuubiran.ezxhelper.init.InitFields.appContext

object CommonPath {
    //P: storage/emulated/0/Android/data/${HostAppPackageName}
    val sAndroidDataDir by lazy {
        appContext.externalCacheDir?.parentFile?.path ?: ""
    }

    //P: data/data/${HostAppPackageName}
    val dDataDir by lazy {
        appContext.filesDir?.parentFile?.path ?: ""
    }
}