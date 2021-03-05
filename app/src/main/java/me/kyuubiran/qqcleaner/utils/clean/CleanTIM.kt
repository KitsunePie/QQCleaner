package me.kyuubiran.qqcleaner.utils.clean

import java.io.File

object CleanTIM {
    //瘦身目录 键值请于arrays.xml里的tim_customer_clean_list_values相同
    private const val CACHES = "caches"
    private const val PICTURE = "picture"
    private const val SHORT_VIDEO = "short_video"

    /**
     * 根据tag获取文件列表
     * @param item Tag
     * @return ArrayList<File>
     */
     fun getFiles(item: String): ArrayList<File> {
        val arr = ArrayList<File>()
        when (item) {
            CACHES -> {

            }
            PICTURE -> {

            }
            SHORT_VIDEO -> {

            }
        }
        return arr
    }

    /**
     * @return 获取TIM普通(一键)瘦身的列表
     */
    fun getHalfList() = ArrayList<File>().apply {
        addAll(getFiles(CACHES))
        addAll(getFiles(PICTURE))
        addAll(getFiles(SHORT_VIDEO))
    }

    /**
     * @return 获取TIM全部(彻底)瘦身的列表
     */
    fun getFullList() = ArrayList<File>().apply {
        addAll(getFiles(CACHES))
        addAll(getFiles(PICTURE))
        addAll(getFiles(SHORT_VIDEO))
    }
}