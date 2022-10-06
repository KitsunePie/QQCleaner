package me.kyuubiran.qqcleaner

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.github.kyuubiran.ezxhelper.utils.parasitics.ActivityProxyManager
import com.github.kyuubiran.ezxhelper.utils.parasitics.FixedClassLoader
import com.github.kyuubiran.ezxhelper.utils.parasitics.TransferActivity

open class BaseActivity : FragmentActivity() {
    override fun getClassLoader(): ClassLoader {
        return FixedClassLoader(
            ActivityProxyManager.MODULE_CLASS_LOADER,
            ActivityProxyManager.HOST_CLASS_LOADER
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val windowState = savedInstanceState.getBundle("android:viewHierarchyState")
        windowState?.let {
            it.classLoader = TransferActivity::class.java.classLoader!!
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}