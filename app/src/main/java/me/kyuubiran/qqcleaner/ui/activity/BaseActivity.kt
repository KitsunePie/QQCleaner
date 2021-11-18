package me.kyuubiran.qqcleaner.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    private val mLoder by lazy { BaseActivity::class.java.classLoader }

    override fun getClassLoader(): ClassLoader = mLoder

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val windowState = savedInstanceState.getBundle("android:viewHierarchyState")
        if (windowState != null) {
            windowState.classLoader = mLoder
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}