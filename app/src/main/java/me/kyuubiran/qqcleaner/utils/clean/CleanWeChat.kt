package me.kyuubiran.qqcleaner.utils.clean

import me.kyuubiran.qqcleaner.utils.appContext
import java.io.File

object CleanWeChat {
    //瘦身目录 键值请于arrays.xml里的we_chat_customer_clean_list_values相同
    private const val CACHES = "caches"
    private const val CARD = "card"
    private const val VIDEO = "video"
    private const val GAME = "game"
    private const val LOG = "log"
    private const val RES_UPDATE = "res_update"
    private const val X5_CORE = "x5_core"

    //    storage/emulated/0/Android/data/com.tencent.mm
    private var rootDataDir: String? = appContext?.externalCacheDir?.parentFile?.path

    //    storage/emulated/0/Android/data/com.tencent.mm/cache
    private var cacheDir: String? = "${rootDataDir}/cache"

    //    storage/emulated/0/Android/data/com.tencent.mm/files
    private var filesDir: String? = "${rootDataDir}/files"

    //    storage/emulated/0/Android/data/com.tencent.mm/files
    private var microMsgDir: String? = "${rootDataDir}/MicroMsgDir"

    private var userDataDirName: String? = null
        get() {
            if (field == null) {
                val dir = File(microMsgDir!!)
                val child = dir.listFiles()
                if (child != null) {
                    for (file in child) {
                        if (file.name.length == 32) {
                            field = file.name
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
                    add(File("$cacheDir"))
                    add(File("${microMsgDir}/CDNTemp"))
                    add(File("${microMsgDir}/FailMsgFileCache"))
                    add(File("${microMsgDir}/${userDataDirName}/webcanvascache"))
                }
            }
            CARD -> {
                arr.apply {
                    add(File("${microMsgDir}/card"))
                }

            }
            VIDEO -> {
                arr.apply {
                    add(File("${microMsgDir}/${userDataDirName}/video"))
                }
            }
            GAME -> {
                arr.apply {
                    add(File("${microMsgDir}/Game"))
                    add(File("${microMsgDir}/wagamefiles"))
                }
            }
            LOG -> {
                arr.apply {
                    add(File("${microMsgDir}/crash"))
                    add(File("${microMsgDir}/xlog"))
                    add(File("${filesDir}/onelog"))
                    add(File("${filesDir}/tbslog"))
                    add(File("${filesDir}/Tencent/tbs_common_log"))
                    add(File("${filesDir}/Tencent/tbs_live_log"))
                }

            }
            RES_UPDATE -> {
                arr.apply {
                    add(File("${microMsgDir}/CheckResUpdate"))
                }
            }
            X5_CORE -> {
                arr.apply {
                    add(File("${rootDataDir}/app_tbs"))
                    add(File("${rootDataDir}/app_tbs_64"))
                    add(File("${rootDataDir}/app_x5webview"))
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
        addAll(getFiles(CARD))
        addAll(getFiles(VIDEO))
        addAll(getFiles(GAME))
        addAll(getFiles(LOG))
        addAll(getFiles(RES_UPDATE))
        addAll(getFiles(X5_CORE))
    }
}