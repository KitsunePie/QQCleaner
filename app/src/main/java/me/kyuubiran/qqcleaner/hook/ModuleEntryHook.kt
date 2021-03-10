package me.kyuubiran.qqcleaner.hook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.core.view.get
import androidx.core.view.size
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import me.kyuubiran.qqcleaner.activity.SettingsActivity
import me.kyuubiran.qqcleaner.data.hostApp
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.secondInitQQ
import me.kyuubiran.qqcleaner.secondInitWeChat
import me.kyuubiran.qqcleaner.utils.*
import me.kyuubiran.qqcleaner.utils.HookUtil.getMethod
import me.kyuubiran.qqcleaner.utils.HookUtil.hookAfter
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
        var count: Int? = null
        arrayOf(
                "Lcom/tencent/mm/plugin/setting/ui/setting/SettingsAboutMicroMsgUI;->onCreate(Landroid/os/Bundle;)V",
                "Lcom/tencent/mm/ui/setting/SettingsAboutMicroMsgUI;->onCreate(Landroid/os/Bundle;)V"
        ).getMethod()?.hookAfter {
            val list = "Lcom/tencent/mm/ui/base/preference/MMPreference;->getListView()Landroid/widget/ListView;"
                            .getMethod()?.invoke(it.thisObject) as ListView
            list.viewTreeObserver.addOnGlobalLayoutListener {
                val adapter = list.adapter as BaseAdapter
                val preferenceClass = loadClass("com.tencent.mm.ui.base.preference.Preference")
                var addMethod: Method? = null
                for (m in getMethods(adapter.javaClass)) {
                    if (m.returnType == Void.TYPE && m.parameterTypes.contentDeepEquals(arrayOf(preferenceClass, Int::class.java)))
                        addMethod = m
                }
                var preference = adapter.getItem(list.size - 2)
                val key = invokeMethod(preference, "getKey")
                if (key == "QQCleaner") {
                    list[list.size - 2].setOnClickListener { v ->
                        if (secondInitWeChat) {
                            val intent = Intent(appContext, SettingsActivity::class.java)
                            v.context.startActivity(intent)
                        } else {
                            appContext?.makeToast("坏耶 资源加载失败惹 重启${hostInfo.hostName}试试吧> <")
                        }
                    }
                }
                if (count == null) count = adapter.count
                else if (adapter.count <= count!!) {
                    val update = adapter.getItem(list.size - 2)
                    val ctx = update.javaClass.getField("mContext").get(update)
                    val entry = update.javaClass.getConstructor(Context::class.java).newInstance(ctx)
                    preference = fieldCpy(update, entry)
                    invokeMethod(
                            preference,
                            "setKey",
                            "QQCleaner",
                            String::class.java
                    )
                    invokeMethod(
                            preference,
                            "setSummary",
                            "芜狐~",
                            CharSequence::class.java
                    )
                    invokeMethod(
                            preference,
                            "setTitle",
                            "${hostInfo.hostName}瘦身",
                            CharSequence::class.java
                    )
                    addMethod?.invoke(adapter, preference, adapter.count + 1)
                }
            }
        }
    }

    private fun hookQQ() {
        for (m in getMethods("com.tencent.mobileqq.activity.AboutActivity")) {
            if (m.name != "doOnCreate") continue
            XposedBridge.hookMethod(m, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    try {
                        val FormSimpleItem = loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
                        var item = getObjectOrNull<View>(param.thisObject, "a", FormSimpleItem)
                        if (item == null) {
                            val ctx = param.thisObject as Activity
                            item =
                                (ctx.window.decorView as ViewGroup).findViewByType(FormSimpleItem)
                        }
                        val entry = newInstance(
                            FormSimpleItem,
                            param.thisObject,
                            Context::class.java
                        ) as View
                        invokeMethod(
                            entry,
                            "setLeftText",
                            "${hostInfo.hostName}瘦身",
                            CharSequence::class.java
                        )
                        invokeMethod(entry, "setRightText", "芜狐~", CharSequence::class.java)
                        val vg = item?.parent as ViewGroup
                        vg.addView(entry, 2)
                        entry.setOnClickListener {
                            if (secondInitQQ) {
                                val intent = Intent(appContext, SettingsActivity::class.java)
                                appContext?.startActivity(intent)
                            } else {
                                appContext?.makeToast("坏耶 资源加载失败惹 重启${hostInfo.hostName}试试吧> <")
                            }
                        }
                    } catch (e: Exception) {
                        loge(e)
                    }
                }
            })
        }
    }
}