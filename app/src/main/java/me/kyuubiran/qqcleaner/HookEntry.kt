package me.kyuubiran.qqcleaner

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

class HookEntry : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        when (lpparam.packageName) {
            "com.tencent.mobileqq",
            "com.tencent.tim" -> {
                HookLoader(lpparam)
            }
        }
    }
}