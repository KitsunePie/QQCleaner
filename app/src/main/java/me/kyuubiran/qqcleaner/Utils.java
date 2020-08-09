package me.kyuubiran.qqcleaner;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.robv.android.xposed.XposedBridge;

//笨比的我不会造轮子从QNotified那里掏过来的
public class Utils {
    public static Field findField(Class<?> clazz, Class<?> type, String name) {
        if (clazz != null && name.length() > 0) {
            Class<?> clz = clazz;
            do {
                for (Field field : clz.getDeclaredFields()) {
                    if ((type == null || field.getType().equals(type)) && field.getName()
                            .equals(name)) {
                        field.setAccessible(true);
                        return field;
                    }
                }
            } while ((clz = clz.getSuperclass()) != null);
            //log(String.format("Can't find the field of type: %s and name: %s in class: %s!",type==null?"[any]":type.getName(),name,clazz.getName()));
        }
        return null;
    }

    public static Object iget_object_or_null(Object obj, String name) {
        return iget_object_or_null(obj, name, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T iget_object_or_null(Object obj, String name, Class<T> type) {
        Class<?> clazz = obj.getClass();
        try {
            Field f = findField(clazz, type, name);
            f.setAccessible(true);
            return (T) f.get(obj);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static void iput_object(Object obj, String name, Object value) {
        iput_object(obj, name, null, value);
    }

    public static void iput_object(Object obj, String name, Class<?> type, Object value) {
        Class<?> clazz = obj.getClass();
        try {
            Field f = findField(clazz, type, name);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    public static String paramsTypesToString(Class<?>... c) {
        if (c == null) return null;
        if (c.length == 0) return "()";
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < c.length; i++) {
            sb.append(c[i] == null ? "[null]" : c[i].getName());
            if (i != c.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static Object new_instance(Class<?> clazz, Object... argsAndTypes) throws InvocationTargetException, InstantiationException, NoSuchMethodException {
        int argc = argsAndTypes.length / 2;
        Class<?>[] argt = new Class[argc];
        Object[] argv = new Object[argc];
        int i;
        Constructor<?> m;
        for (i = 0; i < argc; i++) {
            argt[i] = (Class<?>) argsAndTypes[argc + i];
            argv[i] = argsAndTypes[i];
        }
        m = clazz.getDeclaredConstructor(argt);
        m.setAccessible(true);
        try {
            return m.newInstance(argv);
        } catch (IllegalAccessException e) {
            XposedBridge.log(e);
            //should NOT happen
            throw new RuntimeException(e);
        }
    }

    public static Object invoke_virtual(Object obj, String name, Object... argsTypesAndReturnType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IllegalArgumentException {
        Class<?> clazz = obj.getClass();
        int argc = argsTypesAndReturnType.length / 2;
        Class<?>[] argt = new Class[argc];
        Object[] argv = new Object[argc];
        Class<?> returnType = null;
        if (argc * 2 + 1 == argsTypesAndReturnType.length)
            returnType = (Class<?>) argsTypesAndReturnType[argsTypesAndReturnType.length - 1];
        int i, ii;
        Method[] m;
        Method method = null;
        Class<?>[] _argt;
        for (i = 0; i < argc; i++) {
            argt[i] = (Class<?>) argsTypesAndReturnType[argc + i];
            argv[i] = argsTypesAndReturnType[i];
        }
        loop_main:
        do {
            m = clazz.getDeclaredMethods();
            loop:
            for (i = 0; i < m.length; i++) {
                if (m[i].getName().equals(name)) {
                    _argt = m[i].getParameterTypes();
                    if (_argt.length == argt.length) {
                        for (ii = 0; ii < argt.length; ii++) {
                            if (!argt[ii].equals(_argt[ii])) continue loop;
                        }
                        if (returnType != null && !returnType.equals(m[i].getReturnType()))
                            continue;
                        method = m[i];
                        break loop_main;
                    }
                }
            }
        } while (!Object.class.equals(clazz = clazz.getSuperclass()));
        if (method == null)
            throw new NoSuchMethodException(name + paramsTypesToString(argt) + " in " + obj.getClass().getName());
        method.setAccessible(true);
        return method.invoke(obj, argv);
    }

    public static void deleteAllFiles(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteAllFiles(f);
            }
            file.delete();
        }
    }

}
