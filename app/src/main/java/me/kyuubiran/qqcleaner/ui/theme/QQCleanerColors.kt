package me.kyuubiran.qqcleaner.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val BlackColorPalette = QQCleanerColors(
    mainThemeColor = Color(0xFF82A8E7),
    eightyPercentThemeColor = Color(0xCC82A8E7),
    sixtyThreePercentThemeColor = Color(0xA182A8E7),
    sixtyPercentThemeColor = Color(0x9982A8E7),
    thirtyEightPercentThemeColor = Color(0x6182A8E7),
    fourPercentThemeColor = Color(0x1482A8E7),
    twoPercentThemeColor = Color(0x0582A8E7),

    firstTextColor = Color(0xFFFFFFFF),
    secondTextColor = Color(0xDEFFFFFF),
    disableSecondTextColor = Color(0x61FFFFFF),
    thirdTextColor = Color(0x99FFFFFF),

    pageBackgroundColor = Color(0xFF000000),
    appBarsAndItemBackgroundColor = Color(0xFF303134),
    dialogBackgroundColor = Color(0xFF3C4043),
    typeBoxBackgroundColor = Color(0xFF4F5355),

    dividerColor = Color(0xFF474C4F),
    rippleColor = Color(0xFF3C4043),
    maskColor = Color(0x61202124),
    whiteColor = Color(0xFFFFFFFF),
    iconQQCleanerRingColor = Color(0xFFB0C8F0),

    itemRightIconColor = Color(0x99FFFFFF),
    itemRightTextColor = Color(0x61FFFFFF),

    switchLineColor = Color(0xFF4F5355),
    switchOffColor = Color(0xFF5F6368),
    switchWhiteOnColor = Color(0xFFFFFFFF),
    switchWhiteOnLineColor = Color(0xDEFFFFFF)
)
val DarkColorPalette = QQCleanerColors(
    mainThemeColor = Color(0XFF82A8E7),
    eightyPercentThemeColor = Color(0xCC82A8E7),
    sixtyThreePercentThemeColor = Color(0xA182A8E7),
    sixtyPercentThemeColor = Color(0x9982A8E7),
    thirtyEightPercentThemeColor = Color(0x6182A8E7),
    fourPercentThemeColor = Color(0x1482A8E7),
    twoPercentThemeColor = Color(0x0582A8E7),

    firstTextColor = Color(0xFFFFFFFF),
    secondTextColor = Color(0xDEFFFFFF),
    disableSecondTextColor = Color(0x61FFFFFF),
    thirdTextColor = Color(0x99FFFFFF),

    pageBackgroundColor = Color(0xFF202124),
    appBarsAndItemBackgroundColor = Color(0xFF303134),
    dialogBackgroundColor = Color(0xFF3C4043),
    typeBoxBackgroundColor = Color(0xFF4F5355),

    dividerColor = Color(0xFF474C4F),
    rippleColor = Color(0xFF3C4043),
    maskColor = Color(0x61202124),
    whiteColor = Color(0xFFFFFFFF),
    iconQQCleanerRingColor = Color(0xFFB0C8F0),

    itemRightIconColor = Color(0x99FFFFFF),
    itemRightTextColor = Color(0x61FFFFFF),

    switchLineColor = Color(0xFF4F5355),
    switchOffColor = Color(0xFF5F6368),
    switchWhiteOnColor = Color(0xFFFFFFFF),
    switchWhiteOnLineColor = Color(0xDEFFFFFF)
)

val LightColorPalette = QQCleanerColors(
    mainThemeColor = Color(0XFF0095FF),
    eightyPercentThemeColor = Color(0xCC0095FF),
    sixtyThreePercentThemeColor = Color(0xA10095FF),
    sixtyPercentThemeColor = Color(0x990095FF),
    thirtyEightPercentThemeColor = Color(0x610095FF),
    fourPercentThemeColor = Color(0x0A0095FF),
    twoPercentThemeColor = Color(0x050095FF),

    firstTextColor = Color(0xFF202124),
    secondTextColor = Color(0xFF3C4043),
    disableSecondTextColor = Color(0x613C4043),
    thirdTextColor = Color(0xFF5F6368),

    pageBackgroundColor = Color(0xFFF7F7F7),
    appBarsAndItemBackgroundColor = Color(0xFFFFFFFF),
    dialogBackgroundColor = Color(0xFFFFFFFF),
    typeBoxBackgroundColor = Color(0xFFF7F7F7),

    dividerColor = Color(0xFFF7F7F7),
    rippleColor = Color(0x143C4043),
    maskColor = Color(0x61202124),
    whiteColor = Color(0xFFFFFFFF),
    iconQQCleanerRingColor = Color(0xFF5FBCFF),

    itemRightIconColor = Color(0xFF5F6368),
    itemRightTextColor = Color(0xFF5F6368),

    switchLineColor = Color(0xFFD6DDE7),
    switchOffColor = Color(0xFFC0CBD9),
    switchWhiteOnColor = Color(0xFFFFFFFF),
    switchWhiteOnLineColor = Color(0xDEFFFFFF)
)

/**
 * 这是颜色对应的类
 */
class QQCleanerColors(
    mainThemeColor: Color,
    eightyPercentThemeColor: Color,
    sixtyThreePercentThemeColor: Color,
    sixtyPercentThemeColor: Color,
    thirtyEightPercentThemeColor: Color,
    fourPercentThemeColor: Color,
    twoPercentThemeColor: Color,

    firstTextColor: Color,
    secondTextColor: Color,
    disableSecondTextColor: Color,
    thirdTextColor: Color,

    pageBackgroundColor: Color,
    appBarsAndItemBackgroundColor: Color,
    dialogBackgroundColor: Color,
    typeBoxBackgroundColor: Color,

    dividerColor: Color,
    rippleColor: Color,
    maskColor: Color,
    whiteColor: Color,
    iconQQCleanerRingColor: Color,

    itemRightIconColor: Color,
    itemRightTextColor: Color,

    switchLineColor: Color,
    switchOffColor: Color,
    switchWhiteOnColor: Color,
    switchWhiteOnLineColor: Color

) {
    var mainThemeColor: Color by mutableStateOf(mainThemeColor)
        private set
    var eightyPercentThemeColor: Color by mutableStateOf(eightyPercentThemeColor)
        private set
    var sixtyThreePercentThemeColor: Color by mutableStateOf(sixtyThreePercentThemeColor)
        private set
    var sixtyPercentThemeColor: Color by mutableStateOf(sixtyPercentThemeColor)
        private set
    var thirtyEightPercentThemeColor: Color by mutableStateOf(thirtyEightPercentThemeColor)
        private set
    var fourPercentThemeColor: Color by mutableStateOf(fourPercentThemeColor)
        private set
    var twoPercentThemeColor: Color by mutableStateOf(twoPercentThemeColor)
        private set


    var firstTextColor: Color by mutableStateOf(firstTextColor)
        private set
    var secondTextColor: Color by mutableStateOf(secondTextColor)
        private set
    var disableSecondTextColor: Color by mutableStateOf(disableSecondTextColor)
        private set
    var thirdTextColor: Color by mutableStateOf(thirdTextColor)
        private set


    var pageBackgroundColor: Color by mutableStateOf(pageBackgroundColor)
        private set
    var appBarsAndItemBackgroundColor: Color by mutableStateOf(appBarsAndItemBackgroundColor)
        private set
    var dialogBackgroundColor: Color by mutableStateOf(dialogBackgroundColor)
        private set
    var typeBoxBackgroundColor: Color by mutableStateOf(typeBoxBackgroundColor)
        private set


    var dividerColor: Color by mutableStateOf(dividerColor)
        private set
    var rippleColor: Color by mutableStateOf(rippleColor)
        private set
    var maskColor: Color by mutableStateOf(maskColor)
        private set
    var whiteColor: Color by mutableStateOf(whiteColor)
        private set
    var iconQQCleanerRingColor: Color by mutableStateOf(iconQQCleanerRingColor)
        private set


    var itemRightIconColor: Color by mutableStateOf(itemRightIconColor)
        private set
    var itemRightTextColor: Color by mutableStateOf(itemRightTextColor)
        private set


    var switchLineColor: Color by mutableStateOf(switchLineColor)
        private set
    var switchOffColor: Color by mutableStateOf(switchOffColor)
        private set
    var switchWhiteOnColor: Color by mutableStateOf(switchWhiteOnColor)
        private set
    var switchWhiteOnLineColor: Color by mutableStateOf(switchWhiteOnLineColor)
        private set
}

