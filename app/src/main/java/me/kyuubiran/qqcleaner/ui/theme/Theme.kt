package me.kyuubiran.qqcleaner.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import me.kyuubiran.qqcleaner.util.ConfigManager

val LocalQQCleanerColors = compositionLocalOf {
    LightColorPalette
}

object QQCleanerColorTheme {
    val colors: QQCleanerColors
        @Composable
        get() = LocalQQCleanerColors.current

    enum class Theme {
        Light,
        Dark,
        System
    }

    @JvmStatic
    fun getCurrentTheme(): Theme {
        return when (ConfigManager.sThemeSelect) {
            1 -> Theme.Dark
            2 -> Theme.System
            else -> Theme.Light
        }
    }
}
