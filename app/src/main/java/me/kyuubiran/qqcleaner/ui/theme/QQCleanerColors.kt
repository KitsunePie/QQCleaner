package me.kyuubiran.qqcleaner.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import me.kyuubiran.qqcleaner.annotation.ThemeColor

val DarkColorPalette = QQCleanerColors(
    background = Color(0xFF303134).convert(ColorSpaces.CieXyz),
    cardBackgroundColor = Color(0xFF202124).convert(ColorSpaces.CieXyz),
    cardBackgroundShadowColor = Color(0xFF202124).convert(ColorSpaces.CieXyz),
    textColor = Color(0xFFFFFFFF).convert(ColorSpaces.CieXyz),
    themeColor = Color(0xFF82A8E7).convert(ColorSpaces.CieXyz),
    themeElevationColor = Color(0xFF82A8E7).convert(ColorSpaces.CieXyz),
    buttonTextColor = Color(0xFFFFFFFF).convert(ColorSpaces.CieXyz),
)

val LightColorPalette = QQCleanerColors(
    background = Color(0xFFFFFFFF).convert(ColorSpaces.CieXyz),
    cardBackgroundColor = Color(0xFFF7F7F7).convert(ColorSpaces.CieXyz),
    cardBackgroundShadowColor = Color(0xFF3C4043).convert(ColorSpaces.CieXyz),
    textColor = Color(0xFF3C4043).convert(ColorSpaces.CieXyz),
    themeColor = Color(0xFF0095FF).convert(ColorSpaces.CieXyz),
    themeElevationColor = Color(0xFF0095FF).convert(ColorSpaces.CieXyz),
    buttonTextColor = Color(0xFFFFFFFF).convert(ColorSpaces.CieXyz),
)


@[Stable ThemeColor]
class QQCleanerColors(
    background: Color,
    cardBackgroundColor: Color,
    cardBackgroundShadowColor: Color,
    textColor: Color,
    themeColor: Color,
    themeElevationColor: Color,
    buttonTextColor: Color
) {
    var background: Color by mutableStateOf(background)
        private set
    var cardBackgroundColor: Color by mutableStateOf(cardBackgroundColor)
        private set
    var cardBackgroundShadowColor: Color by mutableStateOf(cardBackgroundShadowColor)
        private set
    var textColor: Color by mutableStateOf(textColor)
        private set
    var themeColor: Color by mutableStateOf(themeColor)
        private set
    var themeElevationColor: Color by mutableStateOf(themeElevationColor)
        private set
    var buttonTextColor: Color by mutableStateOf(buttonTextColor)
        private set
}

