package me.kyuubiran.qqcleaner.hook

import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.XC_MethodHook
import me.kyuubiran.qqcleaner.BuildConfig
import me.kyuubiran.qqcleaner.HookEntry
import me.kyuubiran.qqcleaner.util.AutoCleanManager
import me.kyuubiran.qqcleaner.util.hostApp
import me.kyuubiran.qqcleaner.util.isQqOrTim
import me.kyuubiran.qqcleaner.util.isWeChat

object ContextHook : BaseHook() {
    override val hookName: String = "BaseContextHook"

    var unhook: XC_MethodHook.Unhook? = null

    override fun init() {
        when {
            hostApp.isQqOrTim -> {
                initQqOrTim()
            }
            hostApp.isWeChat -> {
                initWeChat()
            }
        }
    }

    private fun initQqOrTim() {
        unhook = getMethodByDesc("Lcom/tencent/mobileqq/startup/step/LoadDex;->doStep()Z")
            .hookAfter {
                unhook?.unhook()
                //获取Context
                val context =
                    getFieldByDesc("Lcom/tencent/common/app/BaseApplicationImpl;->sApplication:Lcom/tencent/common/app/BaseApplicationImpl;")
                        .getStaticNonNullAs<Application>()
                //初始化全局Context
                Log.i("Init Context")
                EzXHelperInit.initAppContext(context, addPath = true, initModuleResources = true)
                Log.i("Init ActivityProxyManager")
                EzXHelperInit.initActivityProxyManager(
                    modulePackageName = BuildConfig.APPLICATION_ID,
                    hostActivityProxyName = "com.tencent.mobileqq.activity.photo.CameraPreviewActivity",
                    moduleClassLoader = HookEntry::class.java.classLoader!!,
                    hostClassLoader = context.classLoader
                )
                Log.i("Init ActivitySubActivity")
                EzXHelperInit.initSubActivity()

                AutoCleanManager.initAutoClean
            }
    }

    private fun initWeChat() {
        TODO("Not implement yet")
    }
}