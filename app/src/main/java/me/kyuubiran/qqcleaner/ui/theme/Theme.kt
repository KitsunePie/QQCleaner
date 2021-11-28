package me.kyuubiran.qqcleaner.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import me.kyuubiran.qqcleaner.ui.theme.colors.LightColorPalette
import me.kyuubiran.qqcleaner.ui.theme.colors.QQCleanerColors


private val LocalQQCleanerColors = compositionLocalOf {
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