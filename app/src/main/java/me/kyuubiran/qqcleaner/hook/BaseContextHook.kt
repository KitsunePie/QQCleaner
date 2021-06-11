package me.kyuubiran.qqcleaner.hook

import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.getFieldBySig
import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import com.github.kyuubiran.ezxhelper.utils.getStaticNonNullAs
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import me.kyuubiran.qqcleaner.util.hostApp
import me.kyuubiran.qqcleaner.util.isQqOrTim
import me.kyuubiran.qqcleaner.util.isWeChat

object BaseContextHook : BaseHook() {
    override val hookName: String = "BaseContextHook"

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

    private fun initQqOrTim() {
        getMethodBySig("Lcom/tencent/mobileqq/startup/step/LoadDex;->doStep()Z").also { m ->
            m.hookAfter {
                //获取Context
                val context =
                    getFieldBySig("Lcom/tencent/common/app/BaseApplicationImpl;->sApplication:Lcom/tencent/common/app/BaseApplicationImpl;")
                        .getStaticNonNullAs<Application>()
                //初始化全局Context
                EzXHelperInit.initAppContext(context, true)
                isInited = true
            }
        }
    }

    private fun initWeChat() {
        TODO("Not implement yet")
    }
}