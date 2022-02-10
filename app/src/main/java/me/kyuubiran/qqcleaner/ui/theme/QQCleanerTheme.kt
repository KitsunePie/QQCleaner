package me.kyuubiran.qqcleaner.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import me.kyuubiran.qqcleaner.QQCleanerData.isBlack
import me.kyuubiran.qqcleaner.QQCleanerData.isDark
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.*
import me.kyuubiran.qqcleaner.ui.util.RippleCustomTheme
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
            1 -> Dark
            2 -> System
            else -> Light
        }
    }
}

@Composable
fun QQCleanerTheme(colorTheme: Theme, content: @Composable () -> Unit) {
    isDark = when (colorTheme) {
        Light -> false
        Dark -> true
        System -> isSystemInDarkTheme()
    }
    val targetColors = if (isDark)
        if (isBlack)
            BlackColorPalette
        else
            DarkColorPalette
    else
        LightColorPalette


    // 颜色动画
    val mainThemeColor by animateColorAsState(
        targetValue = targetColors.mainThemeColor,
        animationSpec = tween(600)
    )
    val eightyPercentThemeColor by animateColorAsState(
        targetColors.eightyPercentThemeColor,
        animationSpec = tween(600)
    )
    val sixtyThreePercentThemeColor by animateColorAsState(
        targetColors.sixtyThreePercentThemeColor,
        animationSpec = tween(600)
    )
    val sixtyPercentThemeColor by animateColorAsState(
        targetColors.sixtyPercentThemeColor,
        animationSpec = tween(600)
    )
    val thirtyEightPercentThemeColor by animateColorAsState(
        targetColors.thirtyEightPercentThemeColor,
        animationSpec = tween(600)
    )
    val fourPercentThemeColor by animateColorAsState(
        targetColors.fourPercentThemeColor,
        animationSpec = tween(600)
    )
    val twoPercentThemeColor by animateColorAsState(
        targetColors.twoPercentThemeColor,
        animationSpec = tween(600)
    )


    val firstTextColor by animateColorAsState(
        targetColors.firstTextColor,
        animationSpec = tween(600)
    )
    val secondTextColor by animateColorAsState(
        targetColors.secondTextColor,
        animationSpec = tween(600)
    )
    val disableSecondTextColor by animateColorAsState(
        targetColors.disableSecondTextColor,
        animationSpec = tween(600)
    )
    val thirdTextColor by animateColorAsState(
        targetColors.thirdTextColor,
        animationSpec = tween(600)
    )


    val pageBackgroundColor by animateColorAsState(
        targetColors.pageBackgroundColor,
        animationSpec = tween(600)
    )
    val appBarsAndItemBackgroundColor by animateColorAsState(
        targetColors.appBarsAndItemBackgroundColor,
        animationSpec = tween(600)
    )
    val dialogBackgroundColor by animateColorAsState(
        targetColors.dialogBackgroundColor,
        animationSpec = tween(600)
    )
    val typeBoxBackgroundColor by animateColorAsState(
        targetColors.typeBoxBackgroundColor,
        animationSpec = tween(600)
    )


    val dividerColor by animateColorAsState(
        targetColors.dividerColor,
        animationSpec = tween(600)
    )
    val rippleColor by animateColorAsState(
        targetColors.rippleColor,
        animationSpec = tween(600)
    )
    val maskColor by animateColorAsState(
        targetColors.maskColor,
        animationSpec = tween(600)
    )
    val whiteColor by animateColorAsState(
        targetColors.whiteColor,
        animationSpec = tween(600)
    )
    val iconQQCleanerRingColor by animateColorAsState(
        targetColors.iconQQCleanerRingColor,
        animationSpec = tween(600)
    )

    val itemRightIconColor by animateColorAsState(
        targetColors.itemRightIconColor,
        animationSpec = tween(600)
    )

    val itemRightTextColor by animateColorAsState(
        targetColors.itemRightTextColor,
        animationSpec = tween(600)
    )

    val switchLineColor by animateColorAsState(
        targetColors.switchLineColor,
        animationSpec = tween(600)
    )
    val switchOffColor by animateColorAsState(
        targetColors.switchOffColor,
        animationSpec = tween(600)
    )
    val switchWhiteOnColor by animateColorAsState(
        targetColors.switchWhiteOnColor,
        animationSpec = tween(600)
    )
    val switchWhiteOnLineColor by animateColorAsState(
        targetColors.switchWhiteOnLineColor,
        animationSpec = tween(600)
    )

    val colors = QQCleanerColors(
        mainThemeColor = mainThemeColor,
        eightyPercentThemeColor = eightyPercentThemeColor,
        sixtyThreePercentThemeColor = sixtyThreePercentThemeColor,
        sixtyPercentThemeColor = sixtyPercentThemeColor,
        thirtyEightPercentThemeColor = thirtyEightPercentThemeColor,
        fourPercentThemeColor = fourPercentThemeColor,
        twoPercentThemeColor = twoPercentThemeColor,

        firstTextColor = firstTextColor,
        secondTextColor = secondTextColor,
        disableSecondTextColor = disableSecondTextColor,
        thirdTextColor = thirdTextColor,

        pageBackgroundColor = pageBackgroundColor,
        appBarsAndItemBackgroundColor = appBarsAndItemBackgroundColor,
        dialogBackgroundColor = dialogBackgroundColor,
        typeBoxBackgroundColor = typeBoxBackgroundColor,

        dividerColor = dividerColor,
        rippleColor = rippleColor,
        maskColor = maskColor,
        whiteColor = whiteColor,
        iconQQCleanerRingColor = iconQQCleanerRingColor,

        itemRightIconColor = itemRightIconColor,
        itemRightTextColor = itemRightTextColor,

        switchLineColor = switchLineColor,
        switchOffColor = switchOffColor,
        switchWhiteOnColor = switchWhiteOnColor,
        switchWhiteOnLineColor = switchWhiteOnLineColor
    )
    CompositionLocalProvider(LocalQQCleanerColors provides colors) {
        MaterialTheme(
            colors = lightColors(
                primary = colors.mainThemeColor
            )
        ) {
            val defaultRippleCustomTheme by remember {
                mutableStateOf(RippleCustomTheme(colors.rippleColor))
            }
            CompositionLocalProvider(
                LocalRippleTheme provides defaultRippleCustomTheme,
            ) {
                content()
            }
        }
    }
}


