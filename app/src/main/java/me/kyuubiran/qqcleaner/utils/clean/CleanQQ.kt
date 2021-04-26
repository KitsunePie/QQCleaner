package me.kyuubiran.qqcleaner.utils.clean

import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import java.io.File

object CleanQQ {
    //瘦身目录 键值请于arrays.xml里的qq_customer_clean_list_values相同
    private const val CACHES = "caches"
    private const val PICTURE = "picture"
    private const val SHORT_VIDEO = "short_video"
    private const val ADS = "ads"
    private const val ARK_APP = "ark_app"
    private const val LOG = "log"
    private const val DIY_CARD = "diy_card"
    private const val FONT = "font"
    private const val GIFT = "gift"
    private const val ENTRY_EFFECT = "entry_effect"
    private const val USER_ICON = "user_icon"
    private const val ICON_PENDANT = "icon_pendant"
    private const val USER_BACKGROUND = "user_background"
    private const val STICKER_RECOMMEND = "sticker_recommend"
    private const val STICKER_EMOTION = "sticker_emotion"
    private const val POKE = "poke"
    private const val VIP_ICON = "vip_icon"
    private const val DOU_TU = "dou_tu"
    private const val VIDEO_BACKGROUND = "video_background"
    private const val RECEIVE_FILE_CACHE = "receive_file_cache"
    private const val DEBUG = "debug"
    private const val OTHERS = "others"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq
    private val aRootDataDir: String? = appContext.externalCacheDir?.parentFile?.path

    //    storage/emulated/0/
    private val rootDir: String? = appContext.obbDir?.parentFile?.parentFile?.parentFile?.path

    //    storage/emulated/0/tencent
    private val rootTencentDir = "$rootDir/tencent"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent
    private val TencentDir = "$aRootDataDir/Tencent"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/MobileQQ
    private val MobileQQDir = "$TencentDir/MobileQQ"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/QQ_Images
    private val QQ_ImagesDir = "$aRootDataDir/QQ_Images"

    //    storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv
    private val QQfile_recvDir = "$TencentDir/QQfile_recv"

    /**
     * 根据tag获取文件列表
     * @param item Tag
     * @return ArrayList<File>
     */
    fun getFiles(item: String): ArrayList<File> {
        val arr = ArrayList<File>()
        when (item) {
            //缓存
            CACHES -> {
                arr.apply {
                    add(File("$aRootDataDir/cache"))
                    add(File("$MobileQQDir/diskcache"))
                    add(File("$MobileQQDir/Scribble/ScribbleCache"))
                }
            }
            //图片缓存
            PICTURE -> {
                arr.apply {
                    add(File("$MobileQQDir/photo"))
                    add(File("$MobileQQDir/chatpic"))
                    add(File("$MobileQQDir/thumb"))
                    add(File("$QQ_ImagesDir/QQEditPic"))
                    add(File("$MobileQQDir/hotpic"))
                }
            }
            //短视频
            SHORT_VIDEO -> {
                arr.apply {
                    add(File("$MobileQQDir/shortvideo"))
                    add(File("$aRootDataDir/files/VideoCache"))
                    add(File("$aRootDataDir/files/video_story"))

                }
            }
            //广告
            ADS -> {
                arr.apply {
                    add(File("$MobileQQDir/pddata"))
                    add(File("$MobileQQDir/qbosssplahAD"))
                }
            }
            //小程序
            ARK_APP -> {
                arr.apply {
                    add(File("$TencentDir/mini"))
                }
            }
            //日志
            LOG -> {
                arr.apply {
                    add(File("$rootTencentDir/msflogs"))
                    add(File("$aRootDataDir/files/tbslog"))
                    add(File("$aRootDataDir/files/onelong"))
                    add(File("$aRootDataDir/files/tencent/tbs_common_log"))
                    add(File("$aRootDataDir/files/tencent/tbs_live_log"))
                }
            }
            //diy名片
            DIY_CARD -> {
                arr.apply {
                    add(File("$MobileQQDir/.apollo"))
                    add(File("$MobileQQDir/vas/lottie"))
                }
            }
            //字体
            FONT -> {
                arr.apply {
                    add(File("$MobileQQDir/.hiboom_font"))
                    add(File("$MobileQQDir/.font_info"))
                }
            }
            //礼物
            GIFT -> {
                arr.apply {
                    add(File("$MobileQQDir/.gift"))
                }
            }
            //进场特效
            ENTRY_EFFECT -> {
                arr.apply {
                    add(File("$MobileQQDir/.troop/enter_effects"))
                }
            }
            //头像
            USER_ICON -> {
                arr.apply {
                    add(File("$MobileQQDir/head"))
                    add(File("$aRootDataDir/files/tencent/MobileQQ/head"))
                }
            }
            //挂件
            ICON_PENDANT -> {
                arr.apply {
                    add(File("$MobileQQDir/.pendant"))
                }
            }
            //背景
            USER_BACKGROUND -> {
                arr.apply {
                    add(File("$MobileQQDir/.profilecard"))
                }
            }
            //表情推荐
            STICKER_RECOMMEND -> {
                arr.apply {
                    add(File("$MobileQQDir/.sticker_recommended_pics"))
                    add(File("$MobileQQDir/pe"))
                }
            }
            //聊天表情缓存
            STICKER_EMOTION -> {
                arr.apply {
                    add(File("$MobileQQDir/.emotionsm"))
                }
            }
            //戳一戳
            POKE -> {
                arr.apply {
                    add(File("$MobileQQDir/.vaspoke"))
                    add(File("$MobileQQDir/newpoke"))
                    add(File("$MobileQQDir/poke"))
                }
            }
            //vip图标
            VIP_ICON -> {
                arr.apply {
                    add(File("$MobileQQDir/.vipicon"))
                }
            }
            //斗图
            DOU_TU -> {
                arr.apply {
                    add(File("$MobileQQDir/DoutuRes"))
                }
            }
            //视频通话背景
            VIDEO_BACKGROUND -> {
                arr.apply {
                    add(File("$MobileQQDir/funcall"))
                }
            }
            //接收的文件缓存
            RECEIVE_FILE_CACHE -> {
                arr.apply {
                    add(File("$QQfile_recvDir/trooptmp"))
                    add(File("$QQfile_recvDir/tmp"))
                    add(File("$QQfile_recvDir/thumbnails"))
                }
            }
            //调试数据缓存
            DEBUG -> {
                arr.apply {
                    add(File("$aRootDataDir/avdebug"))
                }
            }
            //其他
            OTHERS -> {
                arr.apply {
                    add(File("$MobileQQDir/qav"))
                    add(File("$MobileQQDir/qqmusic"))
                    add(File("$MobileQQDir/pddata"))
                    add(File("$TencentDir/TMAssistantSDK"))
                    add(File("$aRootDataDir/files/tbs"))
                }
            }
        }
        return arr
    }

    /**
     * @return 获取QQ普通(一键)瘦身的列表
     */
    fun getHalfList() = ArrayList<File>().apply {
        addAll(getFiles(CACHES))
        addAll(getFiles(PICTURE))
        addAll(getFiles(SHORT_VIDEO))
        addAll(getFiles(ADS))
        addAll(getFiles(ARK_APP))
        addAll(getFiles(LOG))
        addAll(getFiles(DIY_CARD))
        addAll(getFiles(USER_BACKGROUND))
        addAll(getFiles(VIDEO_BACKGROUND))
    }

    /**
     * @return 获取QQ全部(彻底)瘦身的列表
     */
    fun getFullList() = ArrayList<File>().apply {
        addAll(getHalfList())
        addAll(getFiles(FONT))
        addAll(getFiles(GIFT))
        addAll(getFiles(ENTRY_EFFECT))
        addAll(getFiles(USER_ICON))
        addAll(getFiles(ICON_PENDANT))
        addAll(getFiles(STICKER_RECOMMEND))
        addAll(getFiles(STICKER_EMOTION))
        addAll(getFiles(POKE))
        addAll(getFiles(VIP_ICON))
        addAll(getFiles(DOU_TU))
        addAll(getFiles(RECEIVE_FILE_CACHE))
        addAll(getFiles(DEBUG))
        addAll(getFiles(OTHERS))
    }
}