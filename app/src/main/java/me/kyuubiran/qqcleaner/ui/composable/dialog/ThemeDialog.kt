package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.dialogButtonBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.DialogTitleStyle


@Composable
fun ThemeDialog(
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

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .height(56.dp)
                .fillMaxWidth()
                .background(
                    color = colors.dialogButtonDefault,
                    shape = dialogButtonBackground
                )
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.light_theme),
                color = colors.dialogButtonTextDefault
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .height(56.dp)
                .fillMaxWidth()
                .background(shape = dialogButtonBackground, color = Color.Transparent)
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.dark_theme))
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .height(56.dp)
                .fillMaxWidth()
                .background(shape = dialogButtonBackground, color = Color.Transparent)
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.follow_system_theme))
        }

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

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .height(56.dp)
                .fillMaxWidth()
                .background(shape = dialogButtonBackground, color = Color.Transparent)
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.use_black_dark_theme))
        }

        DialogButton(false, {})
    }
}