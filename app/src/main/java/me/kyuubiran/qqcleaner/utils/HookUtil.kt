package me.kyuubiran.qqcleaner.utils

import com.github.kyuubiran.ezxhelper.utils.Log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.lang.reflect.Member

object HookUtil {

    internal fun Member.hook(callback: XC_MethodHook) = try {
        XposedBridge.hookMethod(this, callback)
    } catch (thr: Throwable) {
        Log.t(thr)
        null
    }

    internal inline fun Member.hookBefore(crossinline hooker: (XC_MethodHook.MethodHookParam) -> Unit) =
        hook(
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam?) = try {
                    hooker(param!!)
                } catch (thr: Throwable) {
                    Log.t(thr)
                }
            })

    internal inline fun Member.hookAfter(crossinline hooker: (XC_MethodHook.MethodHookParam) -> Unit) =
        hook(object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) = try {
                hooker(param!!)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        })

    internal fun Class<*>.hook(method: String?, vararg args: Any?) = try {
        XposedHelpers.findAndHookMethod(this, method, *args)
    } catch (e: NoSuchMethodError) {
        Log.e(e)
        null
    }
}
