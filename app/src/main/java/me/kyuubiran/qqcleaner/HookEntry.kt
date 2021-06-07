package me.kyuubiran.qqcleaner

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.init.InitFields
import de.robv.android.xposed.IXposedHookInitPackageResources
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import de.robv.android.xposed.callbacks.XC_LoadPackage
import me.kyuubiran.qqcleaner.hook.BaseHook
import me.kyuubiran.qqcleaner.util.HostApp
import me.kyuubiran.qqcleaner.util.hostApp

class HookEntry : IXposedHookZygoteInit, IXposedHookInitPackageResources, IXposedHookLoadPackage {
    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.setModulePath(startupParam.modulePath)
    }

    override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam) {
        EzXHelperInit.setXModuleResources(InitFields.modulePath, resparam.res)
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != lpparam.processName) return
        when (lpparam.packageName) {
            "com.tencent.mobileqq" -> {
                EzXHelperInit.initHandleLoadPackage(lpparam)
                EzXHelperInit.setLogTag("QQCleaner-QQ")
                hostApp = HostApp.QQ
                BaseHook.initHooks()
            }
            "com.tencent.tim" -> {
                EzXHelperInit.initHandleLoadPackage(lpparam)
                EzXHelperInit.setLogTag("QQCleaner-TIM")
                hostApp = HostApp.TIM
                BaseHook.initHooks()
            }
            "com.tencent.mm" -> {
                EzXHelperInit.initHandleLoadPackage(lpparam)
                EzXHelperInit.setLogTag("QQCleaner-WECHAT")
                hostApp = HostApp.WE_CHAT
                BaseHook.initHooks()
            }
        }
    }
}
