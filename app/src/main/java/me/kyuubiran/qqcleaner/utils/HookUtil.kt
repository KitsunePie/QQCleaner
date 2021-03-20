package me.kyuubiran.qqcleaner.utils

import com.github.kyuubiran.ezxhelper.init.InitFields
import com.github.kyuubiran.ezxhelper.utils.Log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.lang.reflect.Field
import java.lang.reflect.Member
import java.lang.reflect.Method

object HookUtil {

    internal fun String.findClass(classLoader: ClassLoader, init: Boolean = false): Class<*> =
        Class.forName(this, init, classLoader)

    internal fun String.getMethod(classLoader: ClassLoader = InitFields.mClassLoader) =
        try {
            DexMethodDescriptor(this).getMethodInstance(classLoader)
        } catch (e: Throwable) {
            null
        }

    internal fun Array<String>.getMethod(classLoader: ClassLoader = InitFields.mClassLoader): Method? {
        this.forEach {
            it.getMethod(classLoader)?.apply {
                return this
            }
        }
        return null
    }

    internal fun String.getField(classLoader: ClassLoader = InitFields.mClassLoader) =
        try {
            DexFieldDescriptor(this).getFieldInstance(classLoader)
        } catch (e: Throwable) {
            null
        }

    internal fun Array<String>.getField(classLoader: ClassLoader = InitFields.mClassLoader): Field? {
        this.forEach {
            it.getField(classLoader)?.apply {
                return this
            }
        }
        return null
    }

    internal fun String.hookMethod(callback: XC_MethodHook) = getMethod()?.hook(callback)

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
