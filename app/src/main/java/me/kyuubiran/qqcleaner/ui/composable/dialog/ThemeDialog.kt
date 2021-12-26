package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.QQCleanerViewModel
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.Dark
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.Light
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.dialogButtonBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.DialogTitleStyle

@Composable
fun ThemeDialog(
    viewModel: QQCleanerViewModel,
    onDismissRequest: () -> Unit,
) {

    // 这里是当前主题的获取
    // 理论应该是获取对应的值，然后给他，需要修改 ksp 生成那部分，暂时先别改他
    // 让 ksp 生成一个对应的System，system 通过系统当前主题指向对应对应的主题
    var theme by remember {
        mutableStateOf(
            // 只在第一次调用
            when (viewModel.theme) {
                Light -> 0
                Dark -> 2
            }
        )
    }
    // 用 state 来进行关闭弹窗是哪个人想出来的主意，真烂，哦，是我自己，那没事了
    val state = remember { mutableStateOf(true) }
    // 这是用 BottomDialog 组合出来的弹窗，BottomDialog 还有一部分功能没加
    BottomDialog(
        onDismissRequest = onDismissRequest,
        state = state,
        dialogHeight = 432f
    ) {
        // 这是顶栏部分，就是 主题风格那块
        Row(
            Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(start = 24.dp, top = 26.dp, end = 24.dp, bottom = 25.dp)

        ) {
            Text(
                text = stringResource(id = R.string.item_theme),
                style = DialogTitleStyle,
                color = colors.textColor
            )
        }
        // 这个是线条的绘制，我实在不明白为啥要写的这么麻烦，等等修它
        Canvas(
            modifier = Modifier
                .padding(top = 4.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
        ) {
            drawRect(
                color = Color(0xFFF7F7F7),
                size = this.size
            )
        }
        // 下面是对应的主题
        ThemeItem(
            text = stringResource(id = R.string.light_theme),
            checked = theme == 0,
            onClick = {
                theme = 0
            })
        ThemeItem(
            text = stringResource(id = R.string.dark_theme),
            checked = theme == 2,
            onClick = {
                theme = 2
            })
        ThemeItem(
            text = stringResource(id = R.string.follow_system_theme),
            onClick = {

            })

        Canvas(
            modifier = Modifier
                .padding(top = 12.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
        ) {
            drawRect(
                color = Color(0xFFF7F7F7),
                size = this.size
            )
        }

        ThemeItem(text = stringResource(id = R.string.use_black_dark_theme))

        DialogButton(
            // 这里进行当前选择和主题是否相同，如果不同则则可以点击选择

            theme != when (viewModel.theme) {
                Light -> 0
                Dark -> 2
            }
        ) {
            when (theme) {
                0 -> viewModel.theme = Light
                2 -> viewModel.theme = Dark
            }
            state.value = false
        }
    }
}

@Composable
private fun ThemeItem(text: String, checked: Boolean = false, onClick: () -> Unit = {}) {
    // 如果添加 animateColorAsState 动画，则在点击确定的时候有概率闪退，我也不知道什么问题，不过这个加不加点击动画都那样不管他
    val backgroundColor = if (checked) colors.dialogButtonDefault else Color.Transparent
    val textColor = if (checked) colors.dialogButtonTextDefault else colors.textColor
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
                .padding(16.dp)

        ) {
            Text(text = text, color = textColor)
        }
    }
}

// 恕我直言，这个水波纹真的不优雅，我想给这xx两巴掌
private object RippleCustomTheme : RippleTheme {

    @Composable
    override fun defaultColor() =
        RippleTheme.defaultRippleColor(
            Color(colors.themeColor.toArgb()),
            lightTheme = true
        )

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleTheme.defaultRippleAlpha(
            Color(colors.themeColor.toArgb()),
            lightTheme = true
        )
}