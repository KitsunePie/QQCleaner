package me.kyuubiran.qqcleaner.utils

import java.io.File
import kotlin.concurrent.thread

object CleanManager {
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
    const val POKE = "poke"
    const val VIP_ICON = "vip_icon"
    const val DOU_TU = "dou_tu"
    const val VIDEO_BACKGROUND = "video_background"
    const val RECEIVE_FILE_CACHE = "receive_file_cache"
    const val OTHERS = "others"

    private fun getFiles(item: String): ArrayList<File> {
        val arr = ArrayList<File>()
        when (item) {
            CACHES -> {
                arr.add(File("$rootDataDir/cache"))
                arr.add(File("$MobileQQ/diskcache"))
                arr.add(File("$MobileQQ/Scribble/ScribbleCache"))
            }
            PICTURE -> {
                arr.add(File("$MobileQQ/photo"))
                arr.add(File("$MobileQQ/chatpic"))
                arr.add(File("$MobileQQ/thumb"))
                arr.add(File("$QQ_Images/QQEditPic"))
                arr.add(File("$MobileQQ/hotpic"))
            }
            SHORT_VIDEO -> {
                arr.add(File("$TencentDir/shortvideo"))
            }
            ADS -> {
                arr.add(File("$MobileQQ/qbosssplahAD"))
                arr.add(File("$MobileQQ/pddata"))
            }
            ARK_APP -> {
                arr.add(File("$TencentDir/mini"))
            }
            WEB -> {
                arr.add(File("$rootTencentDir/msflogs/com/tencent/mobileqq"))
            }
            DIY_CARD -> {
                arr.add(File("$MobileQQ/.apollo"))
            }
            FONT -> {
                arr.add(File("$MobileQQ/.font_info"))
                arr.add(File("$MobileQQ/.hiboom_font"))
            }
            GIFT -> {
                arr.add(File("$MobileQQ/.gift"))
            }
            ENTRY_EFFECT -> {
                arr.add(File("$MobileQQ/.troop/enter_effects"))
            }
            USER_ICON -> {
                arr.add(File("$MobileQQ/head"))
            }
            ICON_PENDANT -> {
                arr.add(File("$MobileQQ/.pendant"))
            }
            USER_BACKGROUND -> {
                arr.add(File("$MobileQQ/.profilecard"))
            }
            STICKER_RECOMMEND -> {
                arr.add(File("$MobileQQ/.sticker_recommended_pics"))
                arr.add(File("$MobileQQ/pe"))
            }
            POKE -> {
                arr.add(File("$MobileQQ/.vaspoke"))
                arr.add(File("$MobileQQ/newpoke"))
                arr.add(File("$MobileQQ/poke"))
            }
            VIP_ICON -> {
                arr.add(File("$MobileQQ/.vipicon"))
            }
            DOU_TU -> {
                arr.add(File("$MobileQQ/DoutuRes"))
            }
            VIDEO_BACKGROUND -> {
                arr.add(File("$MobileQQ/funcall"))
            }
            RECEIVE_FILE_CACHE -> {
                arr.add(File("$QQfile_recv/trooptmp"))
                arr.add(File("$QQfile_recv/tmp"))
                arr.add(File("$QQfile_recv/thumbnails"))
            }
            OTHERS -> {
                arr.add(File("$MobileQQ/qav"))
                arr.add(File("$MobileQQ/qqmusic"))
            }
        }
        return arr
    }


    //根目录
    private var rootDataDir: String? = qqContext?.externalCacheDir?.parentFile?.path
    private var rootDir: String? = qqContext?.obbDir?.parentFile?.parentFile?.parentFile?.path
    private var rootTencentDir = "$rootDir/tencent"
    private var TencentDir = "$rootDataDir/Tencent"
    private var MobileQQ = "$TencentDir/MobileQQ"
    private var QQ_Images = "$rootDataDir/QQ_Images"
    private var QQfile_recv = "$TencentDir/QQfile_recv"

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

    private fun getFullList(): ArrayList<File> {
        val arr = ArrayList<File>()
        arr.addAll(getHalfList())
        arr.addAll(getFiles(FONT))
        arr.addAll(getFiles(GIFT))
        arr.addAll(getFiles(ENTRY_EFFECT))
        arr.addAll(getFiles(USER_ICON))
        arr.addAll(getFiles(ICON_PENDANT))
        arr.addAll(getFiles(STICKER_RECOMMEND))
        arr.addAll(getFiles(POKE))
        arr.addAll(getFiles(VIP_ICON))
        arr.addAll(getFiles(DOU_TU))
        arr.addAll(getFiles(RECEIVE_FILE_CACHE))
        arr.addAll(getFiles(OTHERS))
        return arr
    }


    fun autoClean() {

    }

    fun halfClean() {
        doClean(getHalfList())
    }

    fun fullClean() {
        doClean(getFullList())
    }

    fun customerClean() {

    }

    fun doClean(files: ArrayList<File>) {
        thread {
            try {
                qqContext?.showToastBySystem("好耶 开始清理了")
                for (f in files) {
                    deleteAllFiles(f)
                }
                qqContext?.showToastBySystem("好耶 清理完毕了")
            } catch (e: Exception) {
                loge(e)
                qqContext?.showToastBySystem("坏耶 清理失败了")
            }
        }
    }

    private fun deleteAllFiles(file: File) {
        if (file.isFile) {
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
}