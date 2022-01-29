package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.QQCleanerData.theme
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.composable.Line
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.*
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.dialogButtonBackground
import me.kyuubiran.qqcleaner.ui.util.RippleCustomTheme
import me.kyuubiran.qqcleaner.util.ConfigManager

@Composable
fun ThemeDialog(
    onDismissRequest: () -> Unit
) {

    // 这里是当前主题的获取
    // 理论应该是获取对应的值，然后给他，需要修改 ksp 生成那部分，暂时先别改他
    // 让 ksp 生成一个对应的System，system 通过系统当前主题指向对应对应的主题
    var theme by remember {
        mutableStateOf(
            // 只在第一次调用
            when (theme) {
                Light -> 0x0
                Dark -> 0x1
                System -> 0x2
            }
        )
    }
    // 用 state 来进行关闭弹窗是哪个人想出来的主意，真烂，哦，是我自己，那没事了
    val state = remember { mutableStateOf(true) }
    // 这是用 BottomDialog 组合出来的弹窗，BottomDialog 还有一部分功能没加
    BottomDialog(
        onDismissRequest = onDismissRequest,
        state = state,
        dialogHeight = 432f,
        dialogText = stringResource(id = R.string.item_theme)
    ) {
        // 线条绘制
        Line(
            colors.dialogLineColor,
            Modifier
                .padding(top = 4.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
        )
        // 下面是对应的主题
        ThemeItem(
            text = stringResource(id = R.string.light_theme),
            id = R.drawable.ic_sun,
            checked = theme == 0x0,
            onClick = {
                theme = 0x0
            }
        )
        ThemeItem(
            text = stringResource(id = R.string.dark_theme),
            id = R.drawable.ic_moon,
            checked = theme == 0x1,
            onClick = {
                theme = 0x1
            }
        )
        ThemeItem(
            text = stringResource(id = R.string.follow_system_theme),
            id = R.drawable.ic_android,
            checked = theme == 0x2,
            onClick = {
                theme = 0x2
            }
        )

        // 线条绘制
        Line(
            colors.dialogLineColor,
            modifier = Modifier
                .padding(top = 12.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
        )

        ThemeItem(text = stringResource(id = R.string.use_black_dark_theme), id = R.drawable.ic_a)

        DialogButton(
            // 这里进行当前选择和主题是否相同，如果不同则则可以点击选择
            theme != when (QQCleanerData.theme) {
                Light -> 0x0
                Dark -> 0x1
                System -> 0x2
            }
        ) {
            when (theme) {
                0x0 -> QQCleanerData.theme = Light
                0x1 -> QQCleanerData.theme = Dark
                0x2 -> QQCleanerData.theme = System
            }
            ConfigManager.sThemeSelect = theme
            state.value = false
        }
    }
}

@Composable
private fun ThemeItem(
    @DrawableRes id: Int,
    text: String,
    checked: Boolean = false,
    onClick: () -> Unit = {}
) {
    // 如果添加 animateColorAsState 动画，则在点击确定的时候有概率闪退，我也不知道什么问题，不过这个加不加点击动画都那样不管他
    val backgroundColor = if (checked) colors.dialogButtonDefault else Color.Transparent
    val textColor = if (checked) colors.dialogButtonTextDefault else colors.textColor
    // 替换了原本的颜色
    CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme) {
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .height(56.dp)
                .fillMaxWidth()
                .clip(dialogButtonBackground)
                .background(color = backgroundColor)
                .clickable(
                    onClick = onClick
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = id),
                contentDescription = text + "的图标",
                tint = textColor
            )
            Text(
                text = text,
                color = textColor,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            )
            if (checked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chosen),
                    contentDescription = "选择图标",
                    tint = textColor
                )
            }
        }
    }
}
