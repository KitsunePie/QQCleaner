package me.kyuubiran.qqcleaner.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import me.kyuubiran.qqcleaner.data.hostApp
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.math.BigDecimal

private lateinit var mHandler: Handler
lateinit var clzLoader: ClassLoader

@SuppressLint("StaticFieldLeak")
//宿主全局Context
var appContext: Context? = null

val runtimeProc: Runtime = Runtime.getRuntime()

var sModulePath: String = ""

class Utils(classLoader: ClassLoader) {
    //初始化
    init {
        mHandler = Handler(Looper.getMainLooper())
        clzLoader = classLoader
    }
}

//运行主线程
fun runOnUiThread(r: Runnable) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        r.run()
    } else {
        mHandler.post(r)
    }
}

//显示toast
fun Context.makeToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    if (Looper.getMainLooper() == Looper.myLooper())
        Toast.makeText(this, text, duration).show()
    else runOnUiThread { makeToast(text, duration) }
}

//加载类
fun loadClass(clzName: String): Class<*> {
    return clzLoader.loadClass(clzName)
}

/**
 * @return 方法数组
 */
fun getMethods(clzName: String): Array<Method> {
    return loadClass(clzName).declaredMethods
}

fun getMethods(clazz: Class<*>): Array<Method> {
    return clazz.declaredMethods
}

//From QNotified
fun getMethod(
        obj: Any,
        methodName: String,
        returnType: Class<*>?,
        vararg types: Class<*>?
): Method? {
    for (m in getMethods(obj.javaClass)) {
        val argTypes = m.parameterTypes
        if (m.name != methodName) continue
        if (m.returnType != returnType) continue
        for (type in argTypes.withIndex()) {
            if (type != types[type.index]) continue
        }
        return m
    }
    return null
}

/**
 * @return 属性数组
 */
fun getFields(clzName: String): Array<Field>? {
    return loadClass(clzName).declaredFields
}

fun getFields(clazz: Class<*>): Array<Field>? {
    return clazz.declaredFields
}

//From QNotified
fun getField(clazz: Class<*>, name: String, type: Class<*>? = null): Field? {
    if (name.isNotEmpty()) {
        var clz: Class<*> = clazz
        do {
            for (field in clz.declaredFields) {
                if ((type == null || field.type == type) && (field.name == name)) {
                    field.isAccessible = true
                    return field
                }
            }
            if (clz.superclass == null) break
            clz = clz.superclass
        } while (true)
    }
    return null
}

//From QNotified
fun <T> getObjectOrNull(obj: Any, name: String, type: Class<*>? = null): T? {
    val clazz: Class<Any> = obj.javaClass
    try {
        val f = getField(clazz, name, type)
        f?.isAccessible = true
        return f?.get(obj) as T
    } catch (ignored: Exception) {
    }
    return null
}

//From QNotified
fun invokeMethod(obj: Any, name: String, vararg argsTypesAndReturnType: Any): Any? {
    var clazz: Class<*> = obj.javaClass
    val argSize = argsTypesAndReturnType.size / 2
    val argTypes: Array<Class<*>?> = arrayOfNulls(argSize)
    val argValues = arrayOfNulls<Any>(argSize)
    var returnType: Class<*>? = null
    if (argSize * 2 + 1 == argsTypesAndReturnType.size) returnType =
        argsTypesAndReturnType[argsTypesAndReturnType.size - 1] as Class<*>
    var i: Int
    var ii: Int
    var m: Array<Method>
    var method: Method? = null
    var argTypes2: Array<Class<*>>
    i = 0
    while (i < argSize) {
        argTypes[i] = argsTypesAndReturnType[argSize + i] as Class<*>
        argValues[i] = argsTypesAndReturnType[i]
        i++
    }
    loop_main@ do {
        m = clazz.declaredMethods
        i = 0
        loop@ while (i < m.size) {
            if (m[i].name == name) {
                argTypes2 = m[i].parameterTypes
                if (argTypes2.size == argTypes.size) {
                    ii = 0
                    while (ii < argTypes.size) {
                        if (argTypes[ii] != argTypes2[ii]) {
                            i++
                            continue@loop
                        }
                        ii++
                    }
                    if (returnType != null && returnType != m[i].returnType) {
                        i++
                        continue
                    }
                    method = m[i]
                    break@loop_main
                }
            }
            i++
        }
    } while (Any::class.java != clazz.superclass.also { clazz = it })
    method?.isAccessible = true
    return method?.invoke(obj, *argValues)
}

//From QNotified
fun newInstance(clazz: Class<*>, vararg argsAndTypes: Any?): Any? {
    val argSize: Int = argsAndTypes.size / 2
    val argTypes: Array<Class<*>?> = arrayOfNulls(argSize)
    val argValues = arrayOfNulls<Any>(argSize)
    for (i in 0 until argSize) {
        argTypes[i] = argsAndTypes[argSize + i] as Class<*>
        argValues[i] = argsAndTypes[i]
    }
    val constructor: Constructor<*> = clazz.getDeclaredConstructor(*argTypes)
    constructor.isAccessible = true
    return try {
        constructor.newInstance(*argValues)
    } catch (e: IllegalAccessException) {
        throw RuntimeException(e)
    }
}

//格式化清理空间
fun formatSize(size: Long): String {
    return formatSize(size.toString())
}

//格式化清理空间
fun formatSize(size: String): String {
    val sl = BigDecimal(size)
    val b: BigDecimal
    val result: Double
    val len = size.length
    return when {
        len in 0..3 -> {
            " $sl Byte "
        }
        len in 4..6 -> {
            b = sl.divide(BigDecimal(1_024.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            " $result KB "
        }
        len in 7..9 -> {
            b = sl.divide(BigDecimal(1_048_576.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            " $result MB "
        }
        len in 10..12 -> {
            b = sl.divide(BigDecimal(1_073_741_824.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            " $result GB "
        }
        len > 12 -> {
            b = sl.divide(BigDecimal(1_099_511_627_776.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            " $result TB "
        }
        else -> {
            ""
        }
    }
}

val Method.isStatic: Boolean
    get() = Modifier.isStatic(this.modifiers)

val Method.isPublic: Boolean
    get() = Modifier.isPublic(this.modifiers)

val Method.isPrivate: Boolean
    get() = Modifier.isPrivate(this.modifiers)

fun <T : View> viewCpy(srcView: T): T? {
    return try {
        var clz: Class<*> = srcView.javaClass
        val newView = clz.getConstructor(Context::class.java).newInstance(srcView.context)
        var fields: Array<Field>
        while (Object::class.java != clz) {
            fields = clz.declaredFields
            for (f in fields) {
                f.isAccessible = true
                f.set(newView, f.get(srcView))
            }
            clz = clz.superclass
        }
        newView as T
    } catch (e: Exception) {
        loge(e)
        null
    }
}

//仅限构造无参的类型
fun <T> objCpy(srcObj: T): T? {
    return try {
        var clz: Class<*> = srcObj!!::class.java
        val newObj = clz.newInstance()
        var fields: Array<Field>
        while (Object::class.java != clz) {
            fields = clz.declaredFields
            for (f in fields) {
                f.isAccessible = true
                f.set(newObj, f.get(srcObj))
            }
            clz = clz.superclass
        }
        newObj as T
    } catch (e: Exception) {
        loge(e)
        null
    }
}


fun <T> fieldCpy(srcObj: T, newObj: T): T? {
    return try {
        var clz: Class<*> = srcObj!!::class.java
        var fields: Array<Field>
        while (Object::class.java != clz) {
            fields = clz.declaredFields
            for (f in fields) {
                f.isAccessible = true
                f.set(newObj, f.get(srcObj))
            }
            clz = clz.superclass
        }
        newObj
    } catch (e: Exception) {
        loge(e)
        null
    }
}

//必须在主进程调用
fun getAppRuntime(): Any? {
    return try {
            val mAppRuntime = Class.forName("mqq.app.MobileQQ").getDeclaredField("mAppRuntime")
        mAppRuntime.isAccessible = true
        mAppRuntime.get(appContext)
    } catch (e: Exception) {
        loge(e)
        null
    }
}

fun isInNightMode(): Boolean {
    return try {
        when (hostApp) {
            HostApp.TIM, HostApp.WE_CHAT -> {
                false
            }
            HostApp.QQ -> {
                val themeId = loadClass("com.tencent.mobileqq.theme.ThemeUtil")
                        .getDeclaredMethod("getUserCurrentThemeId", loadClass("mqq.app.AppRuntime"))
                        .invoke(null, getAppRuntime()) as String
                "1103".endsWith(themeId) || "2920".endsWith(themeId)
            }
        }
    } catch (e: Exception) {
        loge(e)
        false
    }
}
/* 工具类 Top-Level */