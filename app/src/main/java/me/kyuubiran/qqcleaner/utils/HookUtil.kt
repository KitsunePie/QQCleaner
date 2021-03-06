package me.kyuubiran.qqcleaner.utils

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.lang.reflect.Field
import java.lang.reflect.Member
import java.lang.reflect.Method

object HookUtil {

    internal fun String.findClass(classLoader: ClassLoader, init: Boolean = false): Class<*> =
        Class.forName(this, init, classLoader)

    internal fun String.getMethod(classLoader: ClassLoader = clzLoader): Method =
        DexMethodDescriptor(this).getMethodInstance(classLoader)

    internal fun String.getField(classLoader: ClassLoader = clzLoader): Field =
        DexFieldDescriptor(this).getFieldInstance(classLoader)

    internal fun String.hookMethod(callback: XC_MethodHook) = getMethod().hook(callback)

    internal fun Member.hook(callback: XC_MethodHook) = try {
        XposedBridge.hookMethod(this, callback)
    } catch (e: Throwable) {
        loge(e)
        null
    }

    internal inline fun Member.hookBefore(crossinline hooker: (XC_MethodHook.MethodHookParam) -> Unit) = hook(
        object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) = try {
                hooker(param!!)
            } catch (e: Throwable) {
                loge(e)
            }
        })

    internal inline fun Member.hookAfter(crossinline hooker: (XC_MethodHook.MethodHookParam) -> Unit) = hook(object : XC_MethodHook() {
        override fun afterHookedMethod(param: MethodHookParam?) = try {
            hooker(param!!)
        } catch (e: Throwable) {
            loge(e)
        }
    })

    internal fun Class<*>.hook(method: String?, vararg args: Any?) = try {
        XposedHelpers.findAndHookMethod(this, method, *args)
    } catch (e: NoSuchMethodError) {
        loge(e)
        null
    }
}
