package me.kyuubiran.qqcleaner.hook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import com.github.kyuubiran.ezxhelper.utils.*
import me.kyuubiran.qqcleaner.MainActivity
import me.kyuubiran.qqcleaner.util.hostApp
import me.kyuubiran.qqcleaner.util.hostAppName
import me.kyuubiran.qqcleaner.util.isQqOrTim
import me.kyuubiran.qqcleaner.util.isWeChat

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
        getMethodBySig("Lcom/tencent/mobileqq/activity/AboutActivity;->doOnCreate(Landroid/os/Bundle;)Z").also { m ->
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
        TODO("Not implement yet")
    }
}