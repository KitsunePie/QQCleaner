package me.kyuubiran.qqcleaner.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf


val LocalQQCleanerColors = compositionLocalOf {
    LightColorPalette
}

object QQCleanerColorTheme {
    val colors: QQCleanerColors
        @Composable
        get() = LocalQQCleanerColors.current

    enum class Theme {
        Light, Dark
    }
}



