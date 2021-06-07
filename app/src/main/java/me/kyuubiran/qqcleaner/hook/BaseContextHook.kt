package me.kyuubiran.qqcleaner.hook

import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.getStaticObjectAs
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.loadClass
import me.kyuubiran.qqcleaner.util.hostApp
import me.kyuubiran.qqcleaner.util.isQqOrTim
import me.kyuubiran.qqcleaner.util.isWeChat

object BaseContextHook : BaseHook() {
    override val hookName: String = "BaseContextHook"

    override fun init() {
        when {
            hostApp.isQqOrTim -> {
                initQqAndTim()
            }
            hostApp.isWeChat -> {
                initWeChat()
            }
        }
    }

    private fun initQqAndTim() {
        findMethodByCondition("com.tencent.mobileqq.startup.step.LoadDex") {
            it.returnType == Boolean::class.java && it.parameterTypes.isEmpty()
        }.also { m ->
            m.hookAfter {
                //加载QQ的基础Application
                val cBaseApplicationImpl =
                    loadClass("com.tencent.common.app.BaseApplicationImpl")
                //获取Context
                val context =
                    cBaseApplicationImpl.getStaticObjectAs<Application>(
                        "sApplication",
                        cBaseApplicationImpl
                    )
                //初始化全局Context
                EzXHelperInit.initAppContext(context)
                isInited = true
            }
        }
    }

    private fun initWeChat() {
        TODO("Not implement yet")
    }
}