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
    dialogEditColor = Color(0xFF4F5355).convert(ColorSpaces.CieXyz),
    dialogEditHintColor = Color(0x61FFFFFF).convert(ColorSpaces.CieXyz),
    transparent = Color(0x00202124).convert(ColorSpaces.CieXyz),
    dialogButtonPress = Color(0xFF82A8E7).convert(ColorSpaces.CieXyz),
    dialogButtonDefault = Color(0x1482A8E7).convert(ColorSpaces.CieXyz),
    dialogButtonTextPress = Color(0xFFFFFFFF).convert(ColorSpaces.CieXyz),
    dialogButtonTextDefault = Color(0xFF82A8E7).convert(ColorSpaces.CieXyz),
    iconColor = Color(0x60FFFFFF).convert(ColorSpaces.CieXyz),
    tipTextColor = Color(0x99FFFFFF).convert(ColorSpaces.CieXyz),
    toggleBorderColor = Color(0xFF5f6368).convert(ColorSpaces.CieXyz),
    toggleOffColor = Color(0xFF5f6368).convert(ColorSpaces.CieXyz),
    toggleOnColor = Color(0xFF82a8e7).convert(ColorSpaces.CieXyz),
    dialogBackgroundColor = Color(0x33202124).convert(ColorSpaces.CieXyz)
)

val LightColorPalette = QQCleanerColors(
    background = Color(0xFFFFFFFF).convert(ColorSpaces.CieXyz),
    cardBackgroundColor = Color(0xFFF7F7F7).convert(ColorSpaces.CieXyz),
    cardBackgroundShadowColor = Color(0xFF3C4043).convert(ColorSpaces.CieXyz),
    textColor = Color(0xFF3C4043).convert(ColorSpaces.CieXyz),
    themeColor = Color(0xFF0095FF).convert(ColorSpaces.CieXyz),
    themeElevationColor = Color(0xFF0095FF).convert(ColorSpaces.CieXyz),
    buttonTextColor = Color(0xFFFFFFFF).convert(ColorSpaces.CieXyz),
    dialogEditColor = Color(0xFFF7F7F7).convert(ColorSpaces.CieXyz),
    dialogEditHintColor = Color(0x613C4043).convert(ColorSpaces.CieXyz),
    transparent = Color(0x00FFFFFF).convert(ColorSpaces.CieXyz),
    dialogButtonPress = Color(0xFF0095FF).convert(ColorSpaces.CieXyz),
    dialogButtonDefault = Color(0x050095FF).convert(ColorSpaces.CieXyz),
    dialogButtonTextPress = Color(0xFFFFFFFF).convert(ColorSpaces.CieXyz),
    dialogButtonTextDefault = Color(0xFF0095FF).convert(ColorSpaces.CieXyz),
    iconColor = Color(0xFF5f6368).convert(ColorSpaces.CieXyz),
    tipTextColor = Color(0xFF5F6368).convert(ColorSpaces.CieXyz),
    toggleBorderColor = Color(0xFFd6dde7).convert(ColorSpaces.CieXyz),
    toggleOffColor = Color(0xFFd6dde7).convert(ColorSpaces.CieXyz),
    toggleOnColor = Color(0xFF2196f3).convert(ColorSpaces.CieXyz),
    dialogBackgroundColor = Color(0x33202124).convert(ColorSpaces.CieXyz)
)


@[Stable ThemeColor]
class QQCleanerColors(
    background: Color,
    cardBackgroundColor: Color,
    cardBackgroundShadowColor: Color,
    textColor: Color,
    themeColor: Color,
    themeElevationColor: Color,
    buttonTextColor: Color,
    dialogEditColor: Color,
    dialogEditHintColor: Color,
    transparent: Color,
    dialogButtonPress: Color,
    dialogButtonDefault: Color,
    dialogButtonTextPress: Color,
    dialogButtonTextDefault: Color,
    iconColor: Color,
    tipTextColor: Color,
    toggleBorderColor: Color,
    toggleOffColor: Color,
    toggleOnColor: Color,
    dialogBackgroundColor: Color
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
    var dialogEditColor: Color by mutableStateOf(dialogEditColor)
        private set
    var dialogEditHintColor: Color by mutableStateOf(dialogEditHintColor)
        private set
    var transparent: Color by mutableStateOf(transparent)
        private set
    var dialogButtonPress: Color by mutableStateOf(dialogButtonPress)
        private set
    var dialogButtonDefault: Color by mutableStateOf(dialogButtonDefault)
        private set
    var dialogButtonTextPress: Color by mutableStateOf(dialogButtonTextPress)
        private set
    var dialogButtonTextDefault: Color by mutableStateOf(dialogButtonTextDefault)
        private set
    var iconColor: Color by mutableStateOf(iconColor)
        private set
    var toggleBorderColor: Color by mutableStateOf(toggleBorderColor)
        private set
    var toggleOffColor: Color by mutableStateOf(toggleOffColor)
        private set
    var toggleOnColor: Color by mutableStateOf(toggleOnColor)
        private set
    var tipTextColor: Color by mutableStateOf(tipTextColor)
        private set
    var dialogBackgroundColor: Color by mutableStateOf(dialogBackgroundColor)
        private set
}

