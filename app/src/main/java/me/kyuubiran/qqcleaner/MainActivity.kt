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
import me.kyuubiran.qqcleaner.theme.QQCleanerColors
import me.kyuubiran.qqcleaner.theme.Theme
import me.kyuubiran.qqcleaner.uitls.IS_BLACK_THEME
import me.kyuubiran.qqcleaner.uitls.THEME_SELECT
import me.kyuubiran.qqcleaner.uitls.dataStore
import me.kyuubiran.qqcleaner.uitls.editData
import me.kyuubiran.qqcleaner.uitls.navigationBarLightOldMode
import me.kyuubiran.qqcleaner.uitls.setNavigationBarTranslation
import me.kyuubiran.qqcleaner.uitls.setStatusBarTranslation
import me.kyuubiran.qqcleaner.uitls.statusBarLightOldMode


class MainActivity : FragmentActivity() {
    val mainViewModel: MainActivityStates by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        // 去除顶栏
        mainViewModel.initViewModel(context = this)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // 导航栏与状态栏沉浸
        setStatusBarTranslation()
        setNavigationBarTranslation()
        statusBarLightOldMode()
        navigationBarLightOldMode()
        super.onCreate(savedInstanceState)
        // 关闭系统暗色模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            this.window.decorView.isForceDarkAllowed = false

        // 加载对应的布局
        this.setContentView(R.layout.main_activity)
        lifecycleScope.launch {
            dataStore.data.first()
        }

        this.addOnConfigurationChangedListener {
            when (it.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    mainViewModel.setSystemTheme(true, this)
                }

                Configuration.UI_MODE_NIGHT_YES -> {
                    mainViewModel.setSystemTheme(false, this)
                }

            }
        }

    }


    class MainActivityStates : ViewModel() {
        private lateinit var themeSelect: StateFlow<QQCleanerColors>

        private var _theme = MutableStateFlow(LightColorPalette)
        // 加载主题
        var theme = _theme

        fun initViewModel(context: Context) {
            viewModelScope.launch(Dispatchers.IO) {

                themeSelect = context.dataStore.data.map { preferences ->
                    val themeSelect = preferences[THEME_SELECT] ?: Theme.Light_THEME.value
                    val isBlackTheme = preferences[IS_BLACK_THEME] ?: false
                    when (themeSelect) {
                        Theme.Light_THEME.value -> LightColorPalette
                        Theme.Dark_THEME.value -> if (isBlackTheme) BlackColorPalette else DarkColorPalette
                        Theme.AUTO_THEME.value -> {
                            when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                                Configuration.UI_MODE_NIGHT_NO -> LightColorPalette
                                Configuration.UI_MODE_NIGHT_YES -> if (isBlackTheme) BlackColorPalette else DarkColorPalette
                                else -> LightColorPalette
                            }
                        }

                        else -> LightColorPalette
                    }
                }.stateIn(this)

                themeSelect.collect {
                    _theme.emit(it)
                }
            }
        }

        fun setTheme(theme: Theme, isBlack: Boolean, context: Context) {
            viewModelScope.launch(Dispatchers.IO) {
                context.editData(THEME_SELECT, theme.value)
                context.editData(IS_BLACK_THEME, isBlack)
            }
        }

        fun setSystemTheme(isLight: Boolean, context: Context) {
            viewModelScope.launch(Dispatchers.IO) {
                val themeSelect = context.dataStore.data.map { preferences ->
                    preferences[THEME_SELECT] ?: Theme.Light_THEME.value
                }.stateIn(this).value
                val isBlackTheme = context.dataStore.data.map { preferences ->
                    preferences[IS_BLACK_THEME] ?: false
                }.stateIn(this).value
                if (themeSelect == Theme.AUTO_THEME.value) {
                    _theme.emit(
                        if (isLight) {
                            LightColorPalette
                        } else {
                            if (isBlackTheme) BlackColorPalette else DarkColorPalette
                        }
                    )

                }
            }


        }
    }

}

