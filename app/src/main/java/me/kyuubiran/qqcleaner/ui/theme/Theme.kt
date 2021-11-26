package me.kyuubiran.qqcleaner.ui.theme


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.Dark
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.Light

private val LightColorPalette = QQCleanerColors(
    background = white,
    cardBackgroundColor = grey,
    titleTextColor = blackText,
    themeColor = icon,
    themeElevationColor = iconElevation
)
private val DarkColorPalette = QQCleanerColors(
    background = black,
    cardBackgroundColor = blackGrey,
    titleTextColor = whiteText,
    themeColor = icon,
    themeElevationColor = iconElevation
)

private val LocalWeComposeColors = compositionLocalOf {
    LightColorPalette
}


object QQCleanerColorTheme {
    val colors: QQCleanerColors
        @Composable
        get() = LocalWeComposeColors.current

    enum class Theme {
        Light, Dark
    }
}

@Stable
class QQCleanerColors(
    background: Color,
    cardBackgroundColor: Color,
    titleTextColor: Color,
    themeColor: Color,
    themeElevationColor: Color
) {
    var background: Color by mutableStateOf(background)
        private set

    var cardBackgroundColor: Color by mutableStateOf(cardBackgroundColor)
        private set

    var titleTextColor: Color by mutableStateOf(titleTextColor)
        private set

    var themeColor: Color by mutableStateOf(themeColor)
        private set
    var themeElevationColor: Color by mutableStateOf(themeElevationColor)
        private set
}

@Composable
fun QQCleanerTheme(
    colorTheme: QQCleanerColorTheme.Theme = Light,
    content: @Composable() () -> Unit
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

    val colors = QQCleanerColors(
        background = background.value,
        cardBackgroundColor = cardBackgroundColor.value,
        titleTextColor = titleTextColor.value,
        themeColor = themeColor.value,
        themeElevationColor = themeElevationColor.value
    )

    CompositionLocalProvider(LocalWeComposeColors provides colors) {
        MaterialTheme(
            shapes = shapes,
            content = content
        )
    }
}

