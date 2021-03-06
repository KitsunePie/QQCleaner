package me.kyuubiran.qqcleaner.hook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import me.kyuubiran.qqcleaner.activity.SettingsActivity
import me.kyuubiran.qqcleaner.data.hostApp
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.secondInit
import me.kyuubiran.qqcleaner.utils.*
import me.kyuubiran.qqcleaner.utils.HookUtil.getMethod
import me.kyuubiran.qqcleaner.utils.HookUtil.hookAfter

//模块入口Hook
class ModuleEntryHook {
    init {
        hook()
    }

    private fun hook() {
        when (hostApp) {
            HostApp.QQ, HostApp.TIM -> hookQQ()
            HostApp.WE_CHAT -> hookWECHAT()
        }
    }

    private fun hookWECHAT() {
        "Lcom/tencent/mm/plugin/setting/ui/setting/SettingsAboutMicroMsgUI;->onCreate(Landroid/os/Bundle;)V"
                .getMethod().hookAfter {
                    appContext?.makeToast("嘎嘎嘎")
                    val list = "Lcom/tencent/mm/ui/base/preference/MMPreference;->getListView()Landroid/widget/ListView;"
                            .getMethod().invoke(it.thisObject) as ListView
                    list.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            val entry = list[list.count - 2] as ViewGroup
                            val title = findViewByText(entry, "检查新版本") as TextView
                            title.doAfterTextChanged { v ->
                                if (v.toString() != "${hostInfo.hostName}瘦身")
                                    title.text = "${hostInfo.hostName}瘦身"
                            }
                            entry.setOnClickListener {
                                if (secondInit) {
                                    val intent = Intent(appContext, SettingsActivity::class.java)
                                    appContext?.startActivity(intent)
                                } else {
                                    appContext?.makeToast("坏耶 资源加载失败惹 重启${hostInfo.hostName}试试吧> <")
                                }
                            }
                            list.addFooterView(entry)
                            list.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    })
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
                            item = findViewByType(ctx.window.decorView as ViewGroup, FormSimpleItem)
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
                            if (secondInit) {
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

        private fun findViewByText(view: ViewGroup, text: String) =
            findViewByCondition(view) {
                it.javaClass == TextView::class.java && (it as TextView).text == text
            }

    private fun findViewByType(view: ViewGroup, clazz: Class<*>) =
            findViewByCondition(view) {
                it.javaClass == clazz
            }

    private fun findViewByCondition(view: ViewGroup, condition: (view: View) -> Boolean): View? {
        view.forEach {
            if (condition(it))
                return it
            val ret = if (it is ViewGroup) {
                findViewByCondition(it, condition)
            } else null
            ret?.let {
                return ret
            }
        }
        return null
    }
}