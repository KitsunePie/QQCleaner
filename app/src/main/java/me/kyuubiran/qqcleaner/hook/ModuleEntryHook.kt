package me.kyuubiran.qqcleaner.hook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import me.kyuubiran.qqcleaner.HookLoader
import me.kyuubiran.qqcleaner.activity.SettingsActivity
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.secondInit
import me.kyuubiran.qqcleaner.utils.*
import me.kyuubiran.qqcleaner.view.forEach

//模块入口Hook
class ModuleEntryHook {
    init {
        hook()
    }

    private fun hook() {
        for (m in getMethods("com.tencent.mobileqq.activity.AboutActivity")) {
            if (m.name != "doOnCreate") continue
            XposedBridge.hookMethod(m, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    try {
                        val FormSimpleItem = loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
                        var item = getObjectOrNull<View>(param.thisObject, "a", FormSimpleItem)
                        if (item == null) {
                            val ctx = param.thisObject as Activity
                            item = findViewByType(ctx.window.decorView as ViewGroup, FormSimpleItem)
                        }
                        val context = item?.context
                        val entry = newInstance(
                            FormSimpleItem,
                            param.thisObject,
                            Context::class.java
                        ) as View
                        invokeMethod(entry, "setLeftText", "${hostInfo.hostName}瘦身", CharSequence::class.java)
                        invokeMethod(entry, "setRightText", "芜狐~", CharSequence::class.java)
                        val vg = item?.parent as ViewGroup
                        vg.addView(entry, 2)
                        entry.setOnClickListener {
                            if (secondInit) {
                                val intent = Intent(appContext, SettingsActivity::class.java)
                                context?.startActivity(intent)
                            } else {
                                appContext?.makeToast("还没有加载好哦~等下再点我吧> <")
                            }
                        }
                    } catch (e: Exception) {
                        loge(e)
                    }
                }
            })
        }
    }

    private fun findViewByType(view: ViewGroup, clazz: Class<*>) : View? {
        view.forEach {
            if (it.javaClass == clazz)
                return it
            val ret = if (it is ViewGroup) {
                findViewByType(it, clazz)
            } else null
            ret?.let {
                return ret
            }
        }
        return null
    }
}