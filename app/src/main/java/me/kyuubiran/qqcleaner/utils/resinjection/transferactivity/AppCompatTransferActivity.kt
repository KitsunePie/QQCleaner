package me.kyuubiran.qqcleaner.utils.resinjection.transferactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 所有在宿主内启动的AppCompatActivity都应该继承与此类 否则会报错
 */
open class AppCompatTransferActivity : AppCompatActivity() {
    override fun getClassLoader(): ClassLoader {
        return AppCompatTransferActivity::class.java.classLoader!!
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val windowState = savedInstanceState.getBundle("android:viewHierarchyState")
        windowState?.let {
            it.classLoader = AppCompatTransferActivity::class.java.classLoader!!
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}