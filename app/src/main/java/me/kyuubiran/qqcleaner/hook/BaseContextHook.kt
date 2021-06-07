package me.kyuubiran.qqcleaner.hook

import me.kyuubiran.qqcleaner.util.hostApp
import me.kyuubiran.qqcleaner.util.isQqOrTim
import me.kyuubiran.qqcleaner.util.isWeChat

object BaseContextHook : BaseHook() {
    override val hookName: String = "BaseContextHook"

    override fun init() {
        when {
            hostApp.isQqOrTim -> {

            }
            hostApp.isWeChat -> {
                TODO("Not implement yet")
            }
        }
    }
}