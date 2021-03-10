package me.kyuubiran.qqcleaner.utils.clean

import me.kyuubiran.qqcleaner.utils.appContext
import java.io.File

object CleanWeChat {
    //瘦身目录 键值请于arrays.xml里的we_chat_customer_clean_list_values相同
    private const val CACHES = "caches"
    private const val PICTURE = "picture"
    private const val AVATAR = "avatar"
    private const val VIDEO = "video"
    private const val MINI = "mini"
    private const val LUCKY_MONEY = "lucky_money"
    private const val LOG = "log"
    private const val RES_UPDATE = "res_update"
    private const val X5_CORE = "x5_core"

    //    storage/emulated/0/Android/data/com.tencent.mm
    private val aDataDir: String? = appContext?.externalCacheDir?.parentFile?.path

    //    storage/emulated/0/Android/data/com.tencent.mm/cache
    private val aCacheDir: String = "${aDataDir}/cache"

    //    storage/emulated/0/Android/data/com.tencent.mm/files
    private val aFilesDir: String = "${aDataDir}/files"

    //    storage/emulated/0/Android/data/com.tencent.mm/files
    private val aMicroMsgDir: String = "${aDataDir}/MicroMsg"

    //    data/data/com.tencent.mm
    private val dDataDir: String? = appContext?.filesDir?.parentFile?.path

    //    data/data/com.tencent.mm/cache
    private val dCache: String = "${dDataDir}/cache"

    //    data/data/com.tencent.mm/MicroMsg
    private val dMicroMsgDir: String = "${dDataDir}/MicroMsg"

    private var aUserDataDirName: String? = null
        get() {
            if (field == null) {
                val dir = File(aMicroMsgDir)
                val child = dir.listFiles()
                if (child != null && child.isNotEmpty()) {
                    for (file in child) {
                        if (file.name.length == 32 && file.isDirectory) {
                            field = file.name
                            break
                        }
                    }
                }
            }
//            logi("用户文件夹获取成功:$field")
            return field
        }

    private var dUserDataDirName: String? = null
        get() {
            if (field == null) {
                val dir = File(dMicroMsgDir)
                val child = dir.listFiles()
                if (child != null && child.isNotEmpty()) {
                    for (file in child) {
                        if (file.name.length == 32 && file.isDirectory) {
                            if (File("${file.path}/account.bin").exists()) {
                                field = file.name
                                break
                            }
                        }
                    }
                }
            }
            return field
        }


    /**
     * 根据tag获取文件列表
     * @param item Tag
     * @return ArrayList<File>
     */
    fun getFiles(item: String): ArrayList<File> {
        val arr = ArrayList<File>()
        when (item) {
            CACHES -> {
                arr.apply {
                    add(File(aCacheDir))
                    add(File("${aMicroMsgDir}/CDNTemp"))
                    add(File("${aMicroMsgDir}/FailMsgFileCache"))
                    add(File("${aMicroMsgDir}/${aUserDataDirName}/webcanvascache"))
                    add(File("${dCache}/temp"))
                }
            }
            PICTURE -> {
                arr.apply {
                    add(File("${dMicroMsgDir}/${dUserDataDirName}/image2"))
                    add(File("${dMicroMsgDir}/tmpScanLicense"))
                }

            }
            AVATAR -> {
                arr.apply {
                    add(File("${dMicroMsgDir}/${dUserDataDirName}/avatar"))
                }
            }
            VIDEO -> {
                arr.apply {
                    add(File("${aMicroMsgDir}/${aUserDataDirName}/video"))
                    add(File("${dCache}/mv_video"))
                }
            }
            MINI -> {
                arr.apply {
                    add(File("${dMicroMsgDir}/${dUserDataDirName}/appbrand/pkg"))
                    add(File("${dMicroMsgDir}/appbrand"))
                }
            }
            LUCKY_MONEY -> {
                arr.apply {
                    add(File("${dMicroMsgDir}/luckymoney"))
                }
            }
            LOG -> {
                arr.apply {
                    add(File("${aMicroMsgDir}/crash"))
                    add(File("${dMicroMsgDir}/crash"))
                    add(File("${aMicroMsgDir}/xlog"))
                    add(File("${dDataDir}/files/xlog"))
                    add(File("${aFilesDir}/onelog"))
                    add(File("${aFilesDir}/tbslog"))
                    add(File("${aFilesDir}/Tencent/tbs_common_log"))
                    add(File("${aFilesDir}/Tencent/tbs_live_log"))
                }

            }
            RES_UPDATE -> {
                arr.apply {
                    add(File("${aMicroMsgDir}/CheckResUpdate"))
                }
            }
            X5_CORE -> {
                arr.apply {
                    add(File("${aDataDir}/app_tbs"))
                    add(File("${dDataDir}/app_tbs"))
                    add(File("${aDataDir}/app_tbs_64"))
                    add(File("${dDataDir}/app_tbs_64"))
                    add(File("${aDataDir}/app_x5webview"))
                    add(File("${dDataDir}/app_x5webview"))
                }
            }
        }
        return arr
    }

    /**
     * @return 获取微信普通(一键)瘦身的列表
     */
    fun getHalfList() = ArrayList<File>().apply {
        addAll(getFiles(CACHES))
        addAll(getFiles(VIDEO))
        addAll(getFiles(LOG))
    }

    /**
     * @return 获取微信全部(彻底)瘦身的列表
     */
    fun getFullList() = ArrayList<File>().apply {
        addAll(getFiles(CACHES))
        addAll(getFiles(PICTURE))
        addAll(getFiles(VIDEO))
        addAll(getFiles(MINI))
        addAll(getFiles(LOG))
        addAll(getFiles(RES_UPDATE))
        addAll(getFiles(X5_CORE))
    }
}