package me.kyuubiran.qqcleaner.util.path

import java.io.File

object WeChatPath {
    //P: storage/emulated/0/Android/data/com.tencent.mm/MicroMsg/${UserDataDirName}
    val publicUserData: Pair<String, String> by lazy {
        "!PublicUserDataDir" to run {
            val dirs = File("${CommonPath.publicData.second}/MicroMsg").listFiles()
            if (dirs != null && dirs.isNotEmpty()) {
                return@run dirs.firstOrNull { it.name.length == 32 && it.isDirectory }?.absolutePath
                    ?: ""
            }
            ""
        }
    }

    //P: data/user/0/com.tencent.mm/MicroMsg/${UserDataDirName}
    val privateUserData: Pair<String, String> by lazy {
        "!PrivateUserDataDir" to run {
            val dirs = File("${CommonPath.privateData.second}/MicroMsg").listFiles()
            if (dirs != null && dirs.isNotEmpty()) {
                return@run dirs.firstOrNull {
                    it.name.length == 32 && it.isDirectory
                            && File("${it.absolutePath}/account.bin").exists()
                }?.absolutePath ?: ""
            }
            ""
        }
    }
}