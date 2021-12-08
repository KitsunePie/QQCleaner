package me.kyuubiran.qqcleaner.util.path

//object WeChatPath {
//    //P: storage/emulated/0/Android/data/com.tencent.mm/MicroMsg/${UserDataDirName}
//    var sUserData: String? = null
//        private set
//        get() {
//            if (field == null) {
//                val dirs = File("${CommonPath.sAndroidDataDir}/MicroMsg").listFiles()
//                if (dirs != null && dirs.isNotEmpty()) {
//                    dirs.forEach { file ->
//                        if (file.name.length == 32 && file.isDirectory) {
//                            field = file.path
//                            return@forEach
//                        }
//                    }
//                }
//            }
//            return field
//        }
//
//    //P: data/data/com.tencent.mm/MicroMsg/${UserDataDirName}
//    var dUserData: String? = null
//        private set
//        get() {
//            if (field == null) {
//                val dirs = File("${CommonPath.dDataDir}/MicroMsg").listFiles()
//                if (dirs != null && dirs.isNotEmpty()) {
//                    dirs.forEach { file ->
//                        if (file.name.length == 32 && file.isDirectory) {
//                            if (File("${file.path}/account.bin").exists()) {
//                                field = file.name
//                                return@forEach
//                            }
//                        }
//                    }
//                }
//            }
//            return field
//        }
//}