package me.kyuubiran.qqcleaner.ui.theme.colors

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import me.kyuubiran.qqcleaner.annotation.ThemeColor

@[Stable ThemeColor]
class QQCleanerColors(
    background: Color,
    cardBackgroundColor: Color,
    titleTextColor: Color,
    themeColor: Color,
    themeElevationColor: Color,
    buttonTextColor: Color,
    tipTextColor: Color
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

    var buttonTextColor: Color by mutableStateOf(buttonTextColor)
        private set

    var tipTextColor: Color by mutableStateOf(tipTextColor)
        private set

}