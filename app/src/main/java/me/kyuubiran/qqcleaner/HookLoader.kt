package me.kyuubiran.qqcleaner

import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.data.init
import me.kyuubiran.qqcleaner.hook.ModuleEntryHook
import me.kyuubiran.qqcleaner.utils.CleanManager
import me.kyuubiran.qqcleaner.utils.ConfigManager
import java.lang.reflect.Field
import java.lang.reflect.Method

private const val QQ_CLEANER_TAG = "QQ_CLEANER_TAG"
private var firstInit = false
var secondInitQQ = false
    private set

class HookLoader(lpparam: XC_LoadPackage.LoadPackageParam) {
    init {
        doInit(lpparam.classLoader)
    }

    private fun initItem() {
        ModuleEntryHook()
        ConfigManager.checkConfigIsExists()
        CleanManager.AutoClean.init()
    }

    private fun doInit(rtLoader: ClassLoader) {
        if (firstInit) return
        try {
            val startup: XC_MethodHook = object : XC_MethodHook(51) {
                override fun afterHookedMethod(param: MethodHookParam) {
                    try {
                        if (secondInitQQ) return
                        val clazz = rtLoader.loadClass("com.tencent.common.app.BaseApplicationImpl")
                        var fsApp: Field? = null
                        for (f in clazz.declaredFields) {
                            if (f.type == clazz) {
                                fsApp = f
                                break
                            }
                        }
                        if (fsApp == null) {
                            throw NoSuchFieldException(
                                "field BaseApplicationImpl.sApplication not found"
                            )
                        }
                        val ctx = fsApp[null] as Application
                        init(ctx)
                        EzXHelperInit.initAppContext(hostInfo.application)
                        EzXHelperInit.initActivityProxyManager(
                            BuildConfig.APPLICATION_ID,
                            "com.tencent.mobileqq.activity.photo.CameraPreviewActivity",
                            HookLoader::class.java.classLoader!!
                        )
                        EzXHelperInit.initSubActivity()
                        EzXHelperInit.addModuleAssetPath(ctx)
                        if ("true" == System.getProperty(QQ_CLEANER_TAG)) return
                        System.setProperty(QQ_CLEANER_TAG, "true")
                        initItem()
                        secondInitQQ = true
                    } catch (thr: Throwable) {
                        Log.t(thr)
                        throw thr
                    }
                }
            }
            val loadDex = rtLoader.loadClass("com.tencent.mobileqq.startup.step.LoadDex")
            val ms = loadDex.declaredMethods
            var m: Method? = null
            for (method in ms) {
                if (method.returnType == Boolean::class.javaPrimitiveType && method.parameterTypes.isEmpty()) {
                    m = method
                    break
                }
            }
            XposedBridge.hookMethod(m, startup)
            firstInit = true
        } catch (e: Throwable) {
            if (e.toString().contains("com.bug.zqq")) return
            if (e.toString().contains("com.google.android.webview")) return
            throw e
        }
    }
}