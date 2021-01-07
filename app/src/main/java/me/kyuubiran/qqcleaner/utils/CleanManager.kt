package me.kyuubiran.qqcleaner.utils


import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CUSTOMER_CLEAN_LIST
import com.alibaba.fastjson.JSONArray
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_AUTO_CLEAN_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CURRENT_CLEANED_TIME
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CUSTOMER_CLEAN_MODE
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_TOTAL_CLEANED_SIZE
import me.kyuubiran.qqcleaner.utils.ConfigManager.getConfig
import me.kyuubiran.qqcleaner.utils.ConfigManager.getLong
import java.io.File
import kotlin.concurrent.thread

/**
 * 如果需要定义一个新的分类 请在瘦身目录那块区域定义一个常量
 * 并且在res/values/arrays.xml下的customer_clean_list和customer_clean_list_value添加对应键值
 * @see getHalfList 添加目录到一键瘦身
 * @see getFullList 添加目录到彻底瘦身
 * @see getFiles 添加清理目录用
 */
object CleanManager {
    //瘦身模式
    const val HALF_MODE = "half_mode"
    const val FULL_MODE = "full_mode"
    const val CUSTOMER_MODE = "customer_mode"

    //瘦身目录
    const val CACHES = "caches"
    const val PICTURE = "picture"
    const val SHORT_VIDEO = "short_video"
    const val ADS = "ads"
    const val ARK_APP = "ark_app"
    const val WEB = "web"
    const val DIY_CARD = "diy_card"
    const val FONT = "font"
    const val GIFT = "gift"
    const val ENTRY_EFFECT = "entry_effect"
    const val USER_ICON = "user_icon"
    const val ICON_PENDANT = "icon_pendant"
    const val USER_BACKGROUND = "user_background"
    const val STICKER_RECOMMEND = "sticker_recommend"
    const val STICKER_EMOTION = "sticker_emotion"
    const val POKE = "poke"
    const val VIP_ICON = "vip_icon"
    const val DOU_TU = "dou_tu"
    const val VIDEO_BACKGROUND = "video_background"
    const val RECEIVE_FILE_CACHE = "receive_file_cache"
    const val OTHERS = "others"

    //计算清理完毕后的释放的空间
    private var size = 0L

    /**
     * 根据tag获取文件列表
     * @param item Tag
     * @return ArrayList<File>
     */
    private fun getFiles(item: String): ArrayList<File> {
        val arr = ArrayList<File>()
        when (item) {
            //缓存
            CACHES -> {
                arr.add(File("$rootDataDir/cache"))
                arr.add(File("$MobileQQDir/diskcache"))
                arr.add(File("$MobileQQDir/Scribble/ScribbleCache"))
            }
            //图片缓存
            PICTURE -> {
                arr.add(File("$MobileQQDir/photo"))
                arr.add(File("$MobileQQDir/chatpic"))
                arr.add(File("$MobileQQDir/thumb"))
                arr.add(File("$QQ_Images/QQEditPic"))
                arr.add(File("$MobileQQDir/hotpic"))
            }
            //短视频
            SHORT_VIDEO -> {
                arr.add(File("$MobileQQDir/shortvideo"))
            }
            //广告
            ADS -> {
                arr.add(File("$MobileQQDir/qbosssplahAD"))
                arr.add(File("$MobileQQDir/pddata"))
            }
            //小程序
            ARK_APP -> {
                arr.add(File("$TencentDir/mini"))
            }
            //网页
            WEB -> {
                arr.add(File("$rootTencentDir/msflogs/com/tencent/mobileqq"))
            }
            //diy名片
            DIY_CARD -> {
                arr.add(File("$MobileQQDir/.apollo"))
                arr.add(File("$MobileQQDir/vas/lottie"))
            }
            //字体
            FONT -> {
                arr.add(File("$MobileQQDir/.font_info"))
                arr.add(File("$MobileQQDir/.hiboom_font"))
            }
            //礼物
            GIFT -> {
                arr.add(File("$MobileQQDir/.gift"))
            }
            //进场特效
            ENTRY_EFFECT -> {
                arr.add(File("$MobileQQDir/.troop/enter_effects"))
            }
            //头像
            USER_ICON -> {
                arr.add(File("$MobileQQDir/head"))
            }
            //挂件
            ICON_PENDANT -> {
                arr.add(File("$MobileQQDir/.pendant"))
            }
            //背景
            USER_BACKGROUND -> {
                arr.add(File("$MobileQQDir/.profilecard"))
            }
            //表情推荐
            STICKER_RECOMMEND -> {
                arr.add(File("$MobileQQDir/.sticker_recommended_pics"))
                arr.add(File("$MobileQQDir/pe"))
            }
            //聊天表情缓存
            STICKER_EMOTION -> {
                arr.add(File("$MobileQQDir/.emotionsm"))
            }
            //戳一戳
            POKE -> {
                arr.add(File("$MobileQQDir/.vaspoke"))
                arr.add(File("$MobileQQDir/newpoke"))
                arr.add(File("$MobileQQDir/poke"))
            }
            //vip图标
            VIP_ICON -> {
                arr.add(File("$MobileQQDir/.vipicon"))
            }
            //斗图
            DOU_TU -> {
                arr.add(File("$MobileQQDir/DoutuRes"))
            }
            //视频通话背景
            VIDEO_BACKGROUND -> {
                arr.add(File("$MobileQQDir/funcall"))
            }
            //接收的文件缓存
            RECEIVE_FILE_CACHE -> {
                arr.add(File("$QQfile_recv/trooptmp"))
                arr.add(File("$QQfile_recv/tmp"))
                arr.add(File("$QQfile_recv/thumbnails"))
            }
            //其他
            OTHERS -> {
                arr.add(File("$MobileQQDir/qav"))
                arr.add(File("$MobileQQDir/qqmusic"))
                arr.add(File("$MobileQQDir/pddata"))
                arr.add(File("$TencentDir/TMAssistantSDK"))
            }
        }
        return arr
    }

    //    storage/emulated/0/Android/data/com.tencent.mobileqq
    private var rootDataDir: String? = qqContext?.externalCacheDir?.parentFile?.path

    //    storage/emulated/0/
    private var rootDir: String? = qqContext?.obbDir?.parentFile?.parentFile?.parentFile?.path

    //    storage/emulated/0/tencent
    private var rootTencentDir = "$rootDir/tencent"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent
    private var TencentDir = "$rootDataDir/Tencent"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/MobileQQ
    private var MobileQQDir = "$TencentDir/MobileQQ"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/QQ_Images
    private var QQ_Images = "$rootDataDir/QQ_Images"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv
    private var QQfile_recv = "$TencentDir/QQfile_recv"

    /**
     * @return 获取普通(一键)瘦身的列表
     */
    private fun getHalfList(): ArrayList<File> {
        val arr = ArrayList<File>()
        arr.addAll(getFiles(CACHES))
        arr.addAll(getFiles(PICTURE))
        arr.addAll(getFiles(SHORT_VIDEO))
        arr.addAll(getFiles(ADS))
        arr.addAll(getFiles(ARK_APP))
        arr.addAll(getFiles(WEB))
        arr.addAll(getFiles(DIY_CARD))
        arr.addAll(getFiles(USER_BACKGROUND))
        arr.addAll(getFiles(VIDEO_BACKGROUND))
        return arr
    }

    /**
     * @return 获取全部(彻底)瘦身的列表
     */
    private fun getFullList(): ArrayList<File> {
        val arr = ArrayList<File>()
        arr.addAll(getHalfList())
        arr.addAll(getFiles(FONT))
        arr.addAll(getFiles(GIFT))
        arr.addAll(getFiles(ENTRY_EFFECT))
        arr.addAll(getFiles(USER_ICON))
        arr.addAll(getFiles(ICON_PENDANT))
        arr.addAll(getFiles(STICKER_RECOMMEND))
        arr.addAll(getFiles(STICKER_EMOTION))
        arr.addAll(getFiles(POKE))
        arr.addAll(getFiles(VIP_ICON))
        arr.addAll(getFiles(DOU_TU))
        arr.addAll(getFiles(RECEIVE_FILE_CACHE))
        arr.addAll(getFiles(OTHERS))
        return arr
    }

    //保存清理的内存
    private fun saveSize() {
        val totalSize = getLong(CFG_TOTAL_CLEANED_SIZE).plus(size)
        ConfigManager.setConfig(CFG_TOTAL_CLEANED_SIZE, totalSize)
    }

    /**
     * @return 获取用户自定义的瘦身列表
     */
    private fun getCustomerList(): ArrayList<File> {
        val customerList = getConfig(CFG_CUSTOMER_CLEAN_LIST) as JSONArray
        val arr = ArrayList<File>()
        for (s in customerList) {
            arr.addAll(getFiles(s.toString()))
        }
        return arr
    }

    /**
     * @param showToast 是否显示Toast
     * 一键瘦身
     */
    fun halfClean(showToast: Boolean = true) {
        doClean(getHalfList(), showToast)
    }

    /**
     * @param showToast 是否显示Toast
     * 彻底瘦身
     */
    fun fullClean(showToast: Boolean = true) {
        doClean(getFullList(), showToast)
    }

    /**
     * @param showToast 是否显示Toast
     * 自定义瘦身
     */
    fun customerClean(showToast: Boolean = true) {
        doClean(getCustomerList(), showToast)
    }

    /**
     * @param files 瘦身列表
     * @param showToast 是否显示toast
     * 执行瘦身的函数
     */
    private fun doClean(files: ArrayList<File>, showToast: Boolean = true) {
        thread {
            size = 0L
            if (showToast) qqContext?.showToastBySystem("好耶 开始清理了!")
            try {
                for (f in files) {
                    deleteAllFiles(f)
                }
                qqContext?.showToastBySystem("好耶 清理完毕了!腾出了${formatSize(size)}空间!")
                saveSize()
            } catch (e: Exception) {
                loge(e)
                qqContext?.showToastBySystem("坏耶 清理失败了!")
            }
        }
    }

    /**
     * @param file 文件/文件夹
     * 递归删除文件
     */
    private fun deleteAllFiles(file: File) {
        if (file.isFile) {
            size += file.length()
            file.delete()
            return
        }
        if (file.isDirectory) {
            val childFile = file.listFiles()
            if (childFile == null || childFile.isEmpty()) {
                file.delete()
                return
            }
            for (f in childFile) {
                deleteAllFiles(f)
            }
            file.delete()
        }
    }

    //自动瘦身的类
    class AutoClean {
        private var time = 0L
        private var mode = ""

        //在QQ加载模块的时候会检测并执行一次
        init {
            time = getLong(CFG_CURRENT_CLEANED_TIME)
            //判断间隔是否大于24小时
            if (getConfig(CFG_AUTO_CLEAN_ENABLED) as Boolean && System.currentTimeMillis() - time > 86400000) {
                mode = getConfig(CFG_CUSTOMER_CLEAN_MODE).toString()
                autoClean()
                time = System.currentTimeMillis()
                ConfigManager.setConfig(CFG_CURRENT_CLEANED_TIME, time)
            }
        }

        //自动瘦身的函数
        private fun autoClean() {
            qqContext?.showToastBySystem("好耶 开始自动清理了!")
            when (mode) {
                FULL_MODE -> {
                    fullClean(false)
                }
                CUSTOMER_MODE -> {
                    customerClean(false)
                }
                else -> {
                    halfClean(false)
                }
            }
        }
    }
}