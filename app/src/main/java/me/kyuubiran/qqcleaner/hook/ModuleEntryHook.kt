package me.kyuubiran.qqcleaner.hook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import me.kyuubiran.qqcleaner.activity.SettingsActivity
import me.kyuubiran.qqcleaner.data.hostApp
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.secondInitQQ
import me.kyuubiran.qqcleaner.secondInitWeChat
import me.kyuubiran.qqcleaner.utils.HookUtil.hookAfter
import me.kyuubiran.qqcleaner.utils.HostApp
import me.kyuubiran.qqcleaner.utils.findViewByType
import java.lang.reflect.Method

//模块入口Hook
class ModuleEntryHook {
    init {
        hook()
    }

    private fun hook() {
        when (hostApp) {
            HostApp.QQ, HostApp.TIM -> hookQQ()
            HostApp.WE_CHAT -> hookWeChat()
        }
    }

    private fun hookWeChat() {
        try {
            val classLoader = appContext.classLoader
            val preferenceClass =
                loadClass("com.tencent.mm.ui.base.preference.Preference", classLoader)
            val actClass = try {
                loadClass(
                    "com.tencent.mm.plugin.setting.ui.setting.SettingsAboutMicroMsgUI",
                    classLoader
                )
            } catch (e: Exception) {
                loadClass("com.tencent.mm.ui.setting.SettingsAboutMicroMsgUI", classLoader)
            }
            actClass.getDeclaredMethod("onResume").hookAfter {
                val list = it.thisObject.invokeMethod("getListView") as ListView
                val adapter = list.adapter as BaseAdapter
                val addMethod: Method = findMethodByCondition(adapter.javaClass) { m ->
                    m.returnType == Void.TYPE && m.parameterTypes.contentDeepEquals(
                        arrayOf(
                            preferenceClass,
                            Int::class.java
                        )
                    )
                }
                val entry =
                    loadClass("com.tencent.mm.ui.base.preference.IconPreference", classLoader)
                        .getConstructor(Context::class.java)
                        .newInstance(it.thisObject)
                entry.apply {
                    invokeMethod(
                        "setKey",
                        arrayOf("QQCleaner"),
                        arrayOf(String::class.java)
                    )
                    invokeMethod(
                        "setSummary",
                        arrayOf("芜狐~"),
                        arrayOf(CharSequence::class.java)
                    )
                    invokeMethod(
                        "setTitle",
                        arrayOf("${hostInfo.hostName}瘦身"),
                        arrayOf(java.lang.CharSequence::class.java)
                    )
                }
                list.viewTreeObserver.addOnGlobalLayoutListener {
                    val preference = adapter.getItem(adapter.count - 2)
                    if (preference.invokeMethod("getKey") != "QQCleaner") {
                        addMethod.invoke(adapter, entry, adapter.count + 1)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            findMethodByCondition(actClass) { m ->
                m.name == "onPreferenceTreeClick" && m.parameterTypes[1].isAssignableFrom(
                    preferenceClass
                )
            }.hookBefore {
                val preference = it.args[1]
                if (preference.invokeMethod("getKey") == "QQCleaner") {
                    openModule(secondInitWeChat, it.thisObject as Activity)
                    it.result = true
                }
            }
        } catch (e: Exception) {
            Log.e(e)
        }
    }

    private fun hookQQ() {
        for (m in getMethods("com.tencent.mobileqq.activity.AboutActivity")) {
            if (m.name != "doOnCreate") continue
            XposedBridge.hookMethod(m, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    try {
                        var cFormSimpleItem =
                            loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
                        try {
                            cFormSimpleItem = loadClass("com.tencent.mobileqq.widget.FormCommonSingleLineItem")
                        } catch (e: Exception) {
                            Unit
                        }
                        var item = param.thisObject.getObjectOrNull("a", cFormSimpleItem) as View?
                        if (item == null) {
                            val ctx = param.thisObject as Activity
                            item =
                                (ctx.window.decorView as ViewGroup).findViewByType(cFormSimpleItem)
                        }
                        val entry = cFormSimpleItem.newInstance(
                            arrayOf(param.thisObject),
                            arrayOf(Context::class.java)
                        ) as View
                        entry.apply {
                            invokeMethod(
                                "setLeftText",
                                arrayOf("${hostInfo.hostName}瘦身"),
                                arrayOf(CharSequence::class.java)
                            )
                            invokeMethod(
                                "setRightText",
                                arrayOf("芜狐~"),
                                arrayOf(CharSequence::class.java)
                            )
                        }
                        val vg = item?.parent as ViewGroup
                        vg.addView(entry, 2)
                        entry.setOnClickListener {
                            openModule(secondInitQQ, it.context)
                        }
                    } catch (e: Exception) {
                        Log.e(e)
                    }
                }
            })
        }
    }

    private fun openModule(check: Boolean, ctx: Context) {
        if (check) {
            val intent = Intent(ctx, SettingsActivity::class.java)
            ctx.startActivity(intent)
        } else {
            appContext.showToast("坏耶 资源加载失败惹 重启${hostInfo.hostName}试试吧> <")
        }
    }
}