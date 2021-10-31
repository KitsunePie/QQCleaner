package me.kyuubiran.qqcleaner

import android.annotation.SuppressLint
import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.data.init
import me.kyuubiran.qqcleaner.hook.ModuleEntryHook
import me.kyuubiran.qqcleaner.utils.*
import me.kyuubiran.qqcleaner.utils.HookUtil.hookAfter

private const val WE_CHAT_CLEANER_TAG = "WECHAT_CLEANER_TAG"
private var firstInit = false
var secondInitWeChat = false
    private set

class WeChatHookLoader {
    init {
        doInit()
    }

    private fun initItem() {
        ModuleEntryHook()
        ConfigManager.checkConfigIsExists()
        CleanManager.AutoClean.init()
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun doInit() {
        if (firstInit) return
        try {
            Application::class.java.getDeclaredMethod("onCreate")
                .hookAfter {
                    if (secondInitWeChat) return@hookAfter
                    val ctx = it.thisObject as Application
                    init(ctx)
                    EzXHelperInit.initAppContext(hostInfo.application)
                    EzXHelperInit.initActivityProxyManager(
                        BuildConfig.APPLICATION_ID,
                        "com.tencent.mm.ui.contact.AddressUI",
                        HookLoader::class.java.classLoader!!
                    )
                    EzXHelperInit.initSubActivity()
                    if ("true" == System.getProperty(WE_CHAT_CLEANER_TAG)) return@hookAfter
                    System.setProperty(WE_CHAT_CLEANER_TAG, "true")
                    initItem()
                    secondInitWeChat = true
                }
            firstInit = true
        } catch (thr: Throwable) {
            if (thr.toString().contains("com.google.android.webview")) return
            Log.e(thr)
            throw thr
        }
    }
}