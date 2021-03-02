package me.kyuubiran.qqcleaner

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import me.kyuubiran.qqcleaner.utils.Host
import me.kyuubiran.qqcleaner.utils.hostApp

class HookEntry : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        when (lpparam.packageName) {
            "com.tencent.mobileqq" -> {
                hostApp = Host.QQ
                HookLoader(lpparam)
            }
//            "com.tencent.tim" -> {
//                hostApp = Host.TIM
//                TODO("Support tim")
//            }
        }
    }
}