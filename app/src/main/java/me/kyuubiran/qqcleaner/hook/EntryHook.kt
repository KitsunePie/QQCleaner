package me.kyuubiran.qqcleaner.hook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import com.github.kyuubiran.ezxhelper.utils.*
import me.kyuubiran.qqcleaner.MainActivity
import me.kyuubiran.qqcleaner.util.hostApp
import me.kyuubiran.qqcleaner.util.hostAppName
import me.kyuubiran.qqcleaner.util.isQqOrTim
import me.kyuubiran.qqcleaner.util.isWeChat
import java.lang.reflect.Method

object EntryHook : BaseHook() {
    override val hookName: String = "EntryHook"

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

    private fun startModuleSettingActivity(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
    }

    private fun initQqOrTim() {
        getMethodByDesc("Lcom/tencent/mobileqq/activity/AboutActivity;->doOnCreate(Landroid/os/Bundle;)Z").also { m ->
            m.hookAfter { param ->
                val cFormSimpleItem = try {
                    loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
                } catch (e: Exception) {
                    loadClass("com.tencent.mobileqq.widget.FormCommonSingleLineItem")
                }
                //获取ViewGroup
                val vg: ViewGroup = try {
                    param.thisObject.getObjectAs("a", cFormSimpleItem)
                } catch (e: Exception) {
                    param.thisObject.getObjectOrNullByType(cFormSimpleItem) as View
                }.parent as ViewGroup
                //创建入口
                val entry = cFormSimpleItem.newInstanceAs<View>(
                    arrayOf(param.thisObject),
                    arrayOf(Context::class.java)
                )!!.also {
                    it.invokeMethod(
                        "setLeftText",
                        arrayOf("${hostAppName}瘦身"),
                        arrayOf(CharSequence::class.java)
                    )
                    it.invokeMethod(
                        "setRightText",
                        arrayOf("芜狐~"),
                        arrayOf(CharSequence::class.java)
                    )
                }
                //设置点击事件
                entry.setOnClickListener {
                    startModuleSettingActivity(param.thisObject as Activity)
                }
                //添加入口
                vg.addView(entry, 2)
            }
        }
    }

    private fun initWeChat() {
        runCatching {
            val actClass = try {
                loadClass("com.tencent.mm.plugin.setting.ui.setting.SettingsAboutMicroMsgUI")
            } catch (e: Exception) {
                // 旧版微信的关于界面
                loadClass("com.tencent.mm.ui.setting.SettingsAboutMicroMsgUI")
            }
            val preferenceClass = loadClass("com.tencent.mm.ui.base.preference.Preference")
            actClass.getDeclaredMethod("onResume").hookAfter {
                val ctx = it.thisObject
                val listView = it.thisObject.invokeMethod("getListView") as? ListView
                    ?: it.thisObject.getObjectAs("list", ListView::class.java)
                // TODO 重构对adapter的修改
                val adapter = listView.adapter as BaseAdapter
                val addMethod: Method = findMethod(adapter.javaClass) {
                    returnType == Void.TYPE && parameterTypes.contentDeepEquals(
                        arrayOf(
                            preferenceClass,
                            Int::class.java
                        )
                    )
                }
                // 构建一个入口
                val entry = loadClass("com.tencent.mm.ui.base.preference.IconPreference")
                    .getConstructor(Context::class.java)
                    .newInstance(ctx).apply {
                        // 设置入口的属性
                        invokeMethod(
                            "setKey",
                            arrayOf("QQCleaner"),
                            arrayOf(String::class.java)
                        )
                        // 新版微信这里坏了
                        invokeMethod(
                            "setSummary",
                            arrayOf("芜狐~"),
                            arrayOf(CharSequence::class.java)
                        )
                        invokeMethod(
                            "setTitle",
                            arrayOf("${hostAppName}瘦身"),
                            arrayOf(java.lang.CharSequence::class.java)
                        )
                    }

                listView.viewTreeObserver.addOnGlobalLayoutListener {
                    val preference = adapter.getItem(adapter.count - 2)
                    val key = preference.invokeMethod("getKey")
                        ?: preference.getObject("mKey")
                    if ("QQCleaner" != key) {
                        addMethod.invoke(adapter, entry, adapter.count + 1)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            // Hook Preference点击事件
            findMethod(actClass) {
                name == "onPreferenceTreeClick"
                        && parameterTypes[1].isAssignableFrom(preferenceClass)
            }.hookBefore {
                val preference = it.args[1]
                val key = preference.invokeMethod("getKey")
                    ?: preference.getObject("mKey")
                if ("QQCleaner" == key) {
                    startModuleSettingActivity(it.thisObject as Activity)
                    it.result = true
                }
            }
            Unit
        }.onFailure {
            Log.e(it)
        }
    }
}