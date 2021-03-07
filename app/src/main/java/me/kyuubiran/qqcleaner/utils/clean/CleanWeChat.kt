package me.kyuubiran.qqcleaner.utils.clean

import me.kyuubiran.qqcleaner.utils.appContext
import java.io.File

object CleanWeChat {
    //瘦身目录 键值请于arrays.xml里的we_chat_customer_clean_list_values相同
    private const val CACHES = "caches"
    private const val PICTURE = "picture"
    private const val SHORT_VIDEO = "short_video"
    private const val LOG = "log"

    //    storage/emulated/0/Android/data/com.tencent.mm
    private var rootDataDir: String? = appContext?.externalCacheDir?.parentFile?.path

    //    storage/emulated/0/Android/data/com.tencent.mm/cache
    private var cacheDir: String? = "${rootDataDir}/cache"

    //    storage/emulated/0/Android/data/com.tencent.mm/files
    private var filesDir: String? = "${rootDataDir}/files"

    //    storage/emulated/0/Android/data/com.tencent.mm/files
    private var microMsgDir: String? = "${rootDataDir}/MicroMsgDir"


    /**
     * 根据tag获取文件列表
     * @param item Tag
     * @return ArrayList<File>
     */
    fun getFiles(item: String): ArrayList<File> {
        val arr = ArrayList<File>()
        when (item) {
            CACHES -> {
                arr.add(File("${cacheDir}/Cache"))
                arr.add(File("${cacheDir}/wxcache"))
            }
            PICTURE -> {

            }
            SHORT_VIDEO -> {

            }
            LOG -> {
                arr.add(File("${microMsgDir}/crash"))
                arr.add(File("${microMsgDir}/xlog"))
                arr.add(File("${filesDir}/onelog"))
                arr.add(File("${filesDir}/tbslog"))
                arr.add(File("${filesDir}/Tencent/tbs_common_log"))
                arr.add(File("${filesDir}/Tencent/tbs_live_log"))
            }
        }
        return arr
    }

    /**
     * @return 获取微信普通(一键)瘦身的列表
     */
    fun getHalfList() = ArrayList<File>().apply {
        addAll(getFiles(CACHES))
        addAll(getFiles(PICTURE))
        addAll(getFiles(SHORT_VIDEO))
    }

    /**
     * @return 获取微信全部(彻底)瘦身的列表
     */
    fun getFullList() = ArrayList<File>().apply {
        addAll(getFiles(CACHES))
        addAll(getFiles(PICTURE))
        addAll(getFiles(SHORT_VIDEO))
        addAll(getFiles(LOG))
    }
}