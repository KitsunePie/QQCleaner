package me.kyuubiran.qqcleaner.hook

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import me.kyuubiran.qqcleaner.activity.SettingsActivity
import me.kyuubiran.qqcleaner.utils.*


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
                        val item = getObjectOrNull<View>(param.thisObject, "a", FormSimpleItem)
                        val context = item?.context
                        val entry = newInstance(FormSimpleItem, param.thisObject, Context::class.java) as View
                        invokeMethod(entry, "setLeftText", "QQ瘦身", CharSequence::class.java)
                        invokeMethod(entry, "setRightText", "芜狐~", CharSequence::class.java)
                        val vg = item?.parent as ViewGroup
                        vg.addView(entry, 2)
                        entry.setOnClickListener {
                            val intent = Intent(qqContext, SettingsActivity::class.java)
                            context?.startActivity(intent)
                        }
                    } catch (e: Exception) {
                        loge(e)
                    }
                }
            })
        }
    }
}