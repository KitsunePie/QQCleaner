package me.kyuubiran.qqcleaner

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.theme.BlackColorPalette
import me.kyuubiran.qqcleaner.theme.DarkColorPalette
import me.kyuubiran.qqcleaner.theme.LightColorPalette
import me.kyuubiran.qqcleaner.theme.Theme
import me.kyuubiran.qqcleaner.uitls.IS_BLACK_THEME
import me.kyuubiran.qqcleaner.uitls.THEME_SELECT
import me.kyuubiran.qqcleaner.uitls.dataStore
import me.kyuubiran.qqcleaner.uitls.editData
import me.kyuubiran.qqcleaner.uitls.navigationBarLightMode
import me.kyuubiran.qqcleaner.uitls.setNavigationBarTranslation
import me.kyuubiran.qqcleaner.uitls.setStatusBarTranslation
import me.kyuubiran.qqcleaner.uitls.statusBarLightMode


class MainActivity : FragmentActivity() {
    private val mainViewModel: MainActivityStates by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        this.setTheme(androidx.appcompat.R.style.Base_Theme_AppCompat_Light)
        // 去除顶栏
        mainViewModel.initViewModel(context = this)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // 导航栏与状态栏沉浸
        setStatusBarTranslation()
        setNavigationBarTranslation()
        // 关闭系统暗色模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            this.window.decorView.isForceDarkAllowed = false

        super.onCreate(savedInstanceState)


        lifecycleScope.launch {
            mainViewModel.colorPalette.collect {
                statusBarLightMode(it == LightColorPalette)
                navigationBarLightMode(it == LightColorPalette)
            }
        }
        // 加载对应的布局
        this.setContentView(R.layout.main_activity)
        lifecycleScope.launch {
            dataStore.data.first()
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        this.lifecycleScope.launch {
            mainViewModel.appTheme.collect {
                if (it.type == Theme.Type.AUTO_THEME) {
                    when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                        Configuration.UI_MODE_NIGHT_NO ->
                            mainViewModel.setSystemTheme(true, it.isBlack)

                        Configuration.UI_MODE_NIGHT_YES ->
                            mainViewModel.setSystemTheme(false, it.isBlack)
                    }
                }
            }
        }
    }


    class MainActivityStates : ViewModel() {
        lateinit var appTheme: StateFlow<Theme>

        private var _colorPalette = MutableStateFlow(LightColorPalette)

        // 加载主题
        var colorPalette = _colorPalette

        fun initViewModel(context: Context) {

            viewModelScope.launch(Dispatchers.IO) {
                appTheme = context.dataStore.data.map { preferences ->
                    val themeValue = preferences[THEME_SELECT] ?: Theme.Type.LIGHT_THEME.value
                    val isBlackTheme = preferences[IS_BLACK_THEME] ?: false
                    Theme(themeValue, isBlackTheme)
                }.stateIn(this)

                appTheme.collect {
                    _colorPalette.emit(it.getColorPalette(context))
                }
            }
        }

        fun setTheme(theme: Theme, context: Context) {
            setTheme(theme.type, theme.isBlack, context)
        }

        private fun setTheme(type: Theme.Type, isBlack: Boolean, context: Context) {
            viewModelScope.launch(Dispatchers.IO) {
                context.editData(THEME_SELECT, type.value)
                context.editData(IS_BLACK_THEME, isBlack)
            }
        }

        fun setSystemTheme(isLight: Boolean, isBlack: Boolean) {
            viewModelScope.launch(Dispatchers.IO) {
                _colorPalette.emit(
                    if (isLight) {
                        LightColorPalette
                    } else {
                        if (isBlack) BlackColorPalette else DarkColorPalette
                    }
                )
            }


        }
    }

}

