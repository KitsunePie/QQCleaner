package me.kyuubiran.qqcleaner.ui.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import me.kyuubiran.qqcleaner.QQCleanerData

/**
 * 就是为了少写几行代码
 * 其实用的地方也不多
 * 就是去掉点击和点击水波纹
 * 如果有其他 View 的时候，他可以保证焦点还在 Compose 上
 */
fun Modifier.noClick(): Modifier = composed {
    this.clickable(
        // 防止击穿
        onClick = {},
        // 去掉点击水波纹
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
}

// 恕我直言，这个水波纹真的不优雅，我想给这xx两巴掌
/**
 * 这是一个水波纹
 * @param color 水波纹颜色
 */
class RippleCustomTheme(private val color: Color) : RippleTheme {
    @Composable
    override fun defaultColor() =
        RippleTheme.defaultRippleColor(
            Color(color = color.toArgb()),
            lightTheme = !QQCleanerData.isDark
        )

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleTheme.defaultRippleAlpha(
            Color(color = color.toArgb()),
            lightTheme = !QQCleanerData.isDark
        )
}