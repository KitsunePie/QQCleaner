package me.kyuubiran.qqcleaner

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

class HookEntry : IXposedHookLoadPackage, IXposedHookZygoteInit {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != lpparam.processName)
            return
        when (lpparam.packageName) {
            "com.tencent.mobileqq",
            "com.tencent.tim" -> {
                EzXHelperInit.initHandleLoadPackage(lpparam)
                EzXHelperInit.setLogTag("QQCleaner")
                HookLoader(lpparam)
            }
            "com.tencent.mm" -> {
                EzXHelperInit.initHandleLoadPackage(lpparam)
                EzXHelperInit.setLogTag("QQCleaner")
                WeChatHookLoader()
            }
        }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
    }
}