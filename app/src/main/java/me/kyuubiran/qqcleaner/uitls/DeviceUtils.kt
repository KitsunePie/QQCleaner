package me.kyuubiran.qqcleaner.uitls


// 检测MIUI
fun isMiuiDevices(): Boolean {
    val c = Class.forName("android.os.SystemProperties")
    val get = c.getMethod("get", String::class.java)
    val miui = get.invoke(c, "ro.miui.ui.version.code") as String
    return miui.isNotEmpty()
}