package me.kyuubiran.qqcleaner.hook

import com.github.kyuubiran.ezxhelper.utils.Log

abstract class BaseHook {
    abstract val hookName: String
    protected var isInited = false

    abstract fun init()

    companion object {
        private val hooks: Array<BaseHook> = arrayOf(
            ContextHook,
            EntryHook,
        )

        fun initHooks() {
            hooks.forEach {
                try {
                    if (it.isInited) return@forEach
                    it.init()
                    it.isInited = true
                    Log.i("Inited hook: ${it.hookName}")
                } catch (e: Exception) {
                    Log.e(e, "Init hook failed: ${it.hookName}")
                }
            }
        }
    }
}



