package me.kyuubiran.qqcleaner.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import me.kyuubiran.qqcleaner.R

open class BaseActivity : ComponentActivity() {

    private val mLoder by lazy { BaseActivity::class.java.classLoader }

    override fun getClassLoader(): ClassLoader = mLoder

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val windowState = savedInstanceState.getBundle("android:viewHierarchyState")
        if (windowState != null) {
            windowState.classLoader = mLoder
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}