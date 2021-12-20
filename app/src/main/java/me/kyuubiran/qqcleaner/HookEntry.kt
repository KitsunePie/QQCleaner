package me.kyuubiran.qqcleaner

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage
import me.kyuubiran.qqcleaner.hook.BaseHook
import me.kyuubiran.qqcleaner.util.HostApp
import me.kyuubiran.qqcleaner.util.hostApp
import me.kyuubiran.qqcleaner.util.hostAppName

class HookEntry : IXposedHookZygoteInit, IXposedHookLoadPackage {
    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
    }

    private fun init(lpparam: XC_LoadPackage.LoadPackageParam, _hostApp: HostApp) {
        EzXHelperInit.initHandleLoadPackage(lpparam)
        hostApp = _hostApp
        EzXHelperInit.setLogTag("QQCleaner-${hostAppName}")
        EzXHelperInit.setLogTag("瘦身模块")
        BaseHook.initHooks()
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != lpparam.processName) return
        when (lpparam.packageName) {
            "com.tencent.mobileqq" -> {
                init(lpparam, HostApp.QQ)
            }
            "com.tencent.tim" -> {
                init(lpparam, HostApp.TIM)
            }
            "com.tencent.mm" -> {
                init(lpparam, HostApp.WE_CHAT)
            }
        }
    }
}
