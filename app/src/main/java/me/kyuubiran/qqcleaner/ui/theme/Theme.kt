package me.kyuubiran.qqcleaner.ui.theme


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.Dark
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.Light
import me.kyuubiran.qqcleaner.ui.theme.colors.DarkColorPalette
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

@Composable
fun QQCleanerTheme(
    colorTheme: QQCleanerColorTheme.Theme = Light,
    content: @Composable () -> Unit
) {
    val targetColors = when (colorTheme) {
        Light -> LightColorPalette
        Dark -> DarkColorPalette
    }

    val background = animateColorAsState(
        targetColors.background,
        TweenSpec(600)
    )

    val cardBackgroundColor = animateColorAsState(
        targetColors.cardBackgroundColor,
        TweenSpec(600)
    )
    val titleTextColor = animateColorAsState(
        targetColors.titleTextColor,
        TweenSpec(600)
    )

    val themeColor = animateColorAsState(
        targetColors.themeColor,
        TweenSpec(600)
    )

    val themeElevationColor = animateColorAsState(
        targetColors.themeElevationColor,
        TweenSpec(600)
    )

    val buttonTextColor = animateColorAsState(
        targetColors.buttonTextColor,
        TweenSpec(600)
    )

    val tipTextColor = animateColorAsState(
        targetColors.tipTextColor,
        TweenSpec(600)
    )
    val colors = QQCleanerColors(
        background = background.value,
        cardBackgroundColor = cardBackgroundColor.value,
        titleTextColor = titleTextColor.value,
        themeColor = themeColor.value,
        themeElevationColor = themeElevationColor.value,
        buttonTextColor = buttonTextColor.value,
        tipTextColor = tipTextColor.value
    )

    CompositionLocalProvider(LocalQQCleanerColors provides colors) {
        MaterialTheme(
            shapes = shapes,
            content = content
        )
    }
}

