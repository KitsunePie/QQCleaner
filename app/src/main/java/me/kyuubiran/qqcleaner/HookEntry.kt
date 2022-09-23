package me.kyuubiran.qqcleaner

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage
import me.kyuubiran.qqcleaner.hook.BaseHook


class HookEntry : IXposedHookZygoteInit, IXposedHookLoadPackage {
    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
    }

    private fun init(lpparam: XC_LoadPackage.LoadPackageParam) {
        EzXHelperInit.initHandleLoadPackage(lpparam)
        EzXHelperInit.setLogTag("QQCleaner")
        EzXHelperInit.setToastTag("瘦身模块")
        BaseHook.initHooks()
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != lpparam.processName) return
        when (lpparam.packageName) {
            "com.tencent.mobileqq" -> init(lpparam)
//            "com.tencent.tim" -> init(lpparam, HostApp.TIM)
//            "com.tencent.mm" -> init(lpparam, HostApp.WE_CHAT)
        }
    }
}
