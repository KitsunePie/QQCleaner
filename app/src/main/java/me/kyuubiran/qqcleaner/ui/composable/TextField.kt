package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.DialogEditStyle

@Composable
fun TextField(
    modifier: Modifier,
    value: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        textStyle = DialogEditStyle.copy(color = colors.secondTextColor),
        modifier = modifier,
        value = value,
        keyboardOptions = keyboardOptions,
        cursorBrush = SolidColor(colors.mainThemeColor),
        onValueChange = onValueChange
    )
}