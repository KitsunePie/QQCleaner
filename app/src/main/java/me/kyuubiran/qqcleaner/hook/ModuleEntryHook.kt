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
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import me.kyuubiran.qqcleaner.activity.SettingsActivity
import me.kyuubiran.qqcleaner.data.hostApp
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.secondInitQQ
import me.kyuubiran.qqcleaner.secondInitWeChat
import me.kyuubiran.qqcleaner.utils.HookUtil.getMethod
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
            var count: Int? = null
            arrayOf(
                "Lcom/tencent/mm/plugin/setting/ui/setting/SettingsAboutMicroMsgUI;->onCreate(Landroid/os/Bundle;)V",
                "Lcom/tencent/mm/ui/setting/SettingsAboutMicroMsgUI;->onCreate(Landroid/os/Bundle;)V"
            ).getMethod()?.hookAfter {
                val list =
                    "Lcom/tencent/mm/ui/base/preference/MMPreference;->getListView()Landroid/widget/ListView;"
                        .getMethod()?.invoke(it.thisObject) as ListView
                list.viewTreeObserver.addOnGlobalLayoutListener {
                    val adapter = list.adapter as BaseAdapter
                    val preferenceClass = loadClass("com.tencent.mm.ui.base.preference.Preference")
                    val addMethod: Method = findMethodByCondition(adapter.javaClass) { m ->
                        m.returnType == Void.TYPE && m.parameterTypes.contentDeepEquals(
                            arrayOf(
                                preferenceClass,
                                Int::class.java
                            )
                        )
                    }
                    val preference = adapter.getItem(list.size - 3)
                    val key = preference.invokeMethod("getKey").toString()
                    if (key == "QQCleaner") {
                        list[list.size - 2].setOnClickListener { v ->
                            if (secondInitWeChat) {
                                val intent = Intent(appContext, SettingsActivity::class.java)
                                v.context.startActivity(intent)
                            } else {
                                appContext.showToast("坏耶 资源加载失败惹 重启${hostInfo.hostName}试试吧> <")
                            }
                        }
                    }
                    if (count == null) count = adapter.count
                    else if (adapter.count <= count!!) {
                        val update = adapter.getItem(list.size - 3)
                        val ctx = update.javaClass.getField("mContext").get(update)
                        var entry = preference::class.java.getConstructor(Context::class.java)
                            .newInstance(ctx)
                        entry = fieldCpy(preference, entry)
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
                        addMethod.invoke(adapter, entry, adapter.count + 1)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(e)
        }
    }
}

private fun hookQQ() {
    for (m in getMethods("com.tencent.mobileqq.activity.AboutActivity")) {
        if (m.name != "doOnCreate") continue
        XposedBridge.hookMethod(m, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                try {
                    val cFormSimpleItem =
                        loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
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
                        if (secondInitQQ) {
                            val intent = Intent(it.context, SettingsActivity::class.java)
                            it.context?.startActivity(intent)
                        } else {
                            appContext.showToast("坏耶 资源加载失败惹 重启${hostInfo.hostName}试试吧> <")
                        }
                    }
                } catch (e: Exception) {
                    Log.e(e)
                }
            }
        })
    }
}