package me.kyuubiran.qqcleaner

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage
import me.kyuubiran.qqcleaner.hook.BaseHook
import me.kyuubiran.qqcleaner.util.HostApp
import me.kyuubiran.qqcleaner.util.hostApp

class HookEntry : IXposedHookZygoteInit, IXposedHookLoadPackage {
    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != lpparam.processName) return
        when (lpparam.packageName) {
            "com.tencent.mobileqq" -> {
                EzXHelperInit.initHandleLoadPackage(lpparam)
                EzXHelperInit.setLogTag("QQCleaner-QQ")
                EzXHelperInit.setLogTag("瘦身模块")
                hostApp = HostApp.QQ
                BaseHook.initHooks()
            }
            "com.tencent.tim" -> {
                EzXHelperInit.initHandleLoadPackage(lpparam)
                EzXHelperInit.setLogTag("QQCleaner-TIM")
                EzXHelperInit.setLogTag("瘦身模块")
                hostApp = HostApp.TIM
                BaseHook.initHooks()
            }
            "com.tencent.mm" -> {
                EzXHelperInit.initHandleLoadPackage(lpparam)
                EzXHelperInit.setLogTag("QQCleaner-WECHAT")
                EzXHelperInit.setLogTag("瘦身模块")
                hostApp = HostApp.WE_CHAT
                BaseHook.initHooks()
            }
        }
    }
}
