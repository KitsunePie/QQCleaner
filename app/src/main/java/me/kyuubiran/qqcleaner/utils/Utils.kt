package me.kyuubiran.qqcleaner.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

private lateinit var mHandler: Handler
lateinit var clzLoader: ClassLoader
var qqContext: Context? = null
var sModulePath: String = ""

class Utils(classLoader: ClassLoader) {
    init {
        mHandler = Handler(Looper.getMainLooper())
        clzLoader = classLoader
    }
}

fun runOnUiThread(r: Runnable) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        r.run()
    } else {
        mHandler.post(r)
    }
}

fun Context.showToastBySystem(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    if (Looper.getMainLooper() == Looper.myLooper())
        Toast.makeText(this, text, duration).show()
    else runOnUiThread { showToastBySystem(text, duration) }
}


fun loadClass(clzName: String): Class<*> {
    return clzLoader.loadClass(clzName)
}

fun getMethods(clzName: String): Array<Method> {
    return loadClass(clzName).declaredMethods
}

fun getMethods(clazz: Class<*>): Array<Method> {
    return clazz.declaredMethods
}

fun getMethod(obj: Any, methodName: String, returnType: Class<*>?, vararg types: Class<*>?): Method? {
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

fun getFields(clzName: String): Array<Field>? {
    return loadClass(clzName).declaredFields
}

fun getFields(clazz: Class<*>): Array<Field>? {
    return clazz.declaredFields
}

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

fun newInstance(clazz: Class<*>, vararg argsAndTypes: Any?): Any? {
    val argSize: Int = argsAndTypes.size / 2
    val argTypes: Array<Class<*>?> = arrayOfNulls(argSize)
    val argValues = arrayOfNulls<Any>(argSize)
    val constructor: Constructor<*>
    for (i in 0 until argSize) {
        argTypes[i] = argsAndTypes[argSize + i] as Class<*>
        argValues[i] = argsAndTypes[i]
    }
    constructor = clazz.getDeclaredConstructor(*argTypes)
    constructor.isAccessible = true
    return try {
        constructor.newInstance(*argValues)
    } catch (e: IllegalAccessException) {
        throw RuntimeException(e)
    }
}

fun Method.isStatic(): Boolean {
    return Modifier.isStatic(this.modifiers)
}

fun Method.isPublic(): Boolean {
    return Modifier.isPublic(this.modifiers)
}

fun Method.isPrivate(): Boolean {
    return Modifier.isPrivate(this.modifiers)
}