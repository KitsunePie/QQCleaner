package me.kyuubiran.qqcleaner.hook

import android.app.Activity
import android.app.AlertDialog
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
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.activity.SettingsActivity
import me.kyuubiran.qqcleaner.data.hostApp
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.secondInitQQ
import me.kyuubiran.qqcleaner.secondInitWeChat
import me.kyuubiran.qqcleaner.utils.HookUtil.hookAfter
import me.kyuubiran.qqcleaner.utils.HostApp
import me.kyuubiran.qqcleaner.utils.findViewByType
import java.lang.IllegalStateException
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
            var count: Int? = null
            val actClass = try {
                loadClass("com.tencent.mm.plugin.setting.ui.setting.SettingsAboutMicroMsgUI", classLoader)
            } catch (e: Exception) {
                loadClass("com.tencent.mm.ui.setting.SettingsAboutMicroMsgUI", classLoader)
            }
            actClass.getDeclaredMethod("onResume").hookAfter {
                val list = it.thisObject.invokeMethod("getListView") as ListView
                list.viewTreeObserver.addOnGlobalLayoutListener {
                    val adapter = list.adapter as BaseAdapter
                    val preferenceClass = loadClass("com.tencent.mm.ui.base.preference.Preference", classLoader)
                    val addMethod: Method = findMethodByCondition(adapter.javaClass) { m ->
                        m.returnType == Void.TYPE && m.parameterTypes.contentDeepEquals(
                                arrayOf(
                                        preferenceClass,
                                        Int::class.java
                                )
                        )
                    }
                    val preference = adapter.getItem(list.size - 2)
                    val key = preference.invokeMethod("getKey").toString()
                    if (key == "QQCleaner") {
                        list[list.size - 2].setOnClickListener { v ->
                            openModule(secondInitWeChat, v.context)
                        }
                    }
                    if (count == null) count = adapter.count
                    else if (adapter.count <= count!!) {
                        val entry = loadClass("com.tencent.mm.ui.base.preference.IconPreference", classLoader)
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
                        addMethod.invoke(adapter, entry, adapter.count + 1)
                        adapter.notifyDataSetChanged()
                    }
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
            try {
                ctx.resources.getString(R.string.res_inject_success)
                val intent = Intent(ctx, SettingsActivity::class.java)
                ctx.startActivity(intent)
            } catch (e: Throwable) {
                AlertDialog.Builder(ctx)
                        .apply {
                            setTitle("提示")
                            setMessage("你似乎才更新了模块,需要重启${hostInfo.hostName}生效！")
                            setPositiveButton("立刻重启") { _, _ ->
                                restartHost(ctx)
                            }
                            setNegativeButton("稍后重启", null)
                            show()
                        }
            }
        } else {
            appContext.showToast("坏耶 资源加载失败惹 重启${hostInfo.hostName}试试吧> <")
        }
    }

    private fun restartHost(context: Context, intent: Intent? = null) {
        val targetIntent = intent
                ?: context.packageManager.getLaunchIntentForPackage(hostInfo.packageName)
                ?: throw IllegalStateException("No launch intent for ${hostInfo.hostName}")
        targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(targetIntent)
        Runtime.getRuntime().exit(0)
    }
}