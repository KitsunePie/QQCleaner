package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.QQCleanerViewModel
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.dialogButtonBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.DialogTitleStyle

@Composable
fun ThemeDialog(
    viewModel: QQCleanerViewModel,
    onDismissRequest: () -> Unit,
) {

    BottomDialog(
        onDismissRequest = onDismissRequest,
        dialogHeight = 432f
    ) {
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
            checked = viewModel.theme == QQCleanerColorTheme.Theme.Light,
            onClick = {

            })
        ThemeItem(
            text = stringResource(id = R.string.dark_theme),
            checked = viewModel.theme == QQCleanerColorTheme.Theme.Dark,
            onClick = {

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

        DialogButton(false) {

        }
    }
}

@Composable
private fun ThemeItem(text: String, checked: Boolean = false, onClick: () -> Unit = {}) {
    val backgroundColor by animateColorAsState(if (checked) colors.dialogButtonDefault else Color.Transparent)
    val textColor by animateColorAsState(if (checked) colors.dialogButtonTextDefault else colors.textColor)
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .height(56.dp)
            .fillMaxWidth()
            .clip(dialogButtonBackground)
            .background(color = backgroundColor)
            .clickable {

                onClick.invoke()
            }
            .padding(16.dp)

    ) {
        Text(text = text, color = textColor)
    }
}