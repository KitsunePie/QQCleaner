package me.kyuubiran.qqcleaner.theme

import android.graphics.Color

// 纯黑主题配色
val BlackColorPalette = QQCleanerColors(
    mainThemeColor = Color.parseColor("#FF82A8E7"),
    eightyPercentThemeColor = Color.parseColor("#CC82A8E7"),
    sixtyThreePercentThemeColor = Color.parseColor("#A182A8E7"),
    sixtyPercentThemeColor = Color.parseColor("#9982A8E7"),
    thirtyEightPercentThemeColor = Color.parseColor("#6182A8E7"),
    fourPercentThemeColor = Color.parseColor("#1482A8E7"),
    twoPercentThemeColor = Color.parseColor("#0582A8E7"),

    firstTextColor = Color.parseColor("#FFFFFFFF"),
    secondTextColor = Color.parseColor("#DEFFFFFF"),
    disableSecondTextColor = Color.parseColor("#61FFFFFF"),
    thirdTextColor = Color.parseColor("#99FFFFFF"),

    pageBackgroundColor = Color.parseColor("#FF000000"),
    appBarsAndItemBackgroundColor = Color.parseColor("#FF303134"),
    dialogBackgroundColor = Color.parseColor("#FF3C4043"),
    typeBoxBackgroundColor = Color.parseColor("#FF4F5355"),

    dividerColor = Color.parseColor("#FF474C4F"),
    rippleColor = Color.parseColor("#FF3C4043"),
    maskColor = Color.parseColor("#61202124"),
    whiteColor = Color.parseColor("#FFFFFFFF"),
    iconQQCleanerRingColor = Color.parseColor("#FFB0C8F0"),

    itemRightIconColor = Color.parseColor("#99FFFFFF"),
    itemRightTextColor = Color.parseColor("#61FFFFFF"),

    switchLineColor = Color.parseColor("#FF4F5355"),
    switchOffColor = Color.parseColor("#FF5F6368"),
    switchWhiteOnColor = Color.parseColor("#FFFFFFFF"),
    switchWhiteOnLineColor = Color.parseColor("#DEFFFFFF")
)

// 默认的暗色主题配色
val DarkColorPalette = QQCleanerColors(
    mainThemeColor = Color.parseColor("#FF82A8E7"),
    eightyPercentThemeColor = Color.parseColor("#CC82A8E7"),
    sixtyThreePercentThemeColor = Color.parseColor("#A182A8E7"),
    sixtyPercentThemeColor = Color.parseColor("#9982A8E7"),
    thirtyEightPercentThemeColor = Color.parseColor("#6182A8E7"),
    fourPercentThemeColor = Color.parseColor("#1482A8E7"),
    twoPercentThemeColor = Color.parseColor("#0582A8E7"),

    firstTextColor = Color.parseColor("#FFFFFFFF"),
    secondTextColor = Color.parseColor("#DEFFFFFF"),
    disableSecondTextColor = Color.parseColor("#61FFFFFF"),
    thirdTextColor = Color.parseColor("#99FFFFFF"),

    pageBackgroundColor = Color.parseColor("#FF202124"),
    appBarsAndItemBackgroundColor = Color.parseColor("#FF303134"),
    dialogBackgroundColor = Color.parseColor("#FF3C4043"),
    typeBoxBackgroundColor = Color.parseColor("#FF4F5355"),

    dividerColor = Color.parseColor("#FF474C4F"),
    rippleColor = Color.parseColor("#FF3C4043"),
    maskColor = Color.parseColor("#61202124"),
    whiteColor = Color.parseColor("#FFFFFFFF"),
    iconQQCleanerRingColor = Color.parseColor("#FFB0C8F0"),

    itemRightIconColor = Color.parseColor("#99FFFFFF"),
    itemRightTextColor = Color.parseColor("#61FFFFFF"),

    switchLineColor = Color.parseColor("#FF4F5355"),
    switchOffColor = Color.parseColor("#FF5F6368"),
    switchWhiteOnColor = Color.parseColor("#FFFFFFFF"),
    switchWhiteOnLineColor = Color.parseColor("#DEFFFFFF")
)

// 亮色主题配色
val LightColorPalette = QQCleanerColors(
    mainThemeColor = Color.parseColor("#FF0095FF"),
    eightyPercentThemeColor = Color.parseColor("#CC0095FF"),
    sixtyThreePercentThemeColor = Color.parseColor("#A10095FF"),
    sixtyPercentThemeColor = Color.parseColor("#990095FF"),
    thirtyEightPercentThemeColor = Color.parseColor("#610095FF"),
    fourPercentThemeColor = Color.parseColor("#0A0095FF"),
    twoPercentThemeColor = Color.parseColor("#050095FF"),

    firstTextColor = Color.parseColor("#FF202124"),
    secondTextColor = Color.parseColor("#FF3C4043"),
    disableSecondTextColor = Color.parseColor("#613C4043"),
    thirdTextColor = Color.parseColor("#FF5F6368"),

    pageBackgroundColor = Color.parseColor("#FFF7F7F7"),
    appBarsAndItemBackgroundColor = Color.parseColor("#FFFFFFFF"),
    dialogBackgroundColor = Color.parseColor("#FFFFFFFF"),
    typeBoxBackgroundColor = Color.parseColor("#FFF7F7F7"),

    dividerColor = Color.parseColor("#FFF7F7F7"),
    rippleColor = Color.parseColor("#143C4043"),
    maskColor = Color.parseColor("#61202124"),
    whiteColor = Color.parseColor("#FFFFFFFF"),
    iconQQCleanerRingColor = Color.parseColor("#FF5FBCFF"),

    itemRightIconColor = Color.parseColor("#FF5F6368"),
    itemRightTextColor = Color.parseColor("#FF5F6368"),

    switchLineColor = Color.parseColor("#FFD6DDE7"),
    switchOffColor = Color.parseColor("#FFC0CBD9"),
    switchWhiteOnColor = Color.parseColor("#FFFFFFFF"),
    switchWhiteOnLineColor = Color.parseColor("#DEFFFFFF")
)

/**
 * 这是颜色对应的类
 */
class QQCleanerColors(
    val mainThemeColor: Int,
    val eightyPercentThemeColor: Int,
    val sixtyThreePercentThemeColor: Int,
    val sixtyPercentThemeColor: Int,
    val thirtyEightPercentThemeColor: Int,
    val fourPercentThemeColor: Int,
    val twoPercentThemeColor: Int,

    val firstTextColor: Int,
    val secondTextColor: Int,
    val disableSecondTextColor: Int,
    val thirdTextColor: Int,

    val pageBackgroundColor: Int,
    val appBarsAndItemBackgroundColor: Int,
    val dialogBackgroundColor: Int,
    val typeBoxBackgroundColor: Int,

    val dividerColor: Int,
    val rippleColor: Int,
    val maskColor: Int,
    val whiteColor: Int,
    iconQQCleanerRingColor: Int,

    val itemRightIconColor: Int,
    val itemRightTextColor: Int,

    val switchLineColor: Int,
    switchOffColor: Int,
    switchWhiteOnColor: Int,
    switchWhiteOnLineColor: Int

)