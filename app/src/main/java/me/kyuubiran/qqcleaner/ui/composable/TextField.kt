package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors

@Composable
fun TextField(
    modifier: Modifier,
    value: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
            color = colors.textColor
        ),
        modifier = modifier,
        value = value,
        keyboardOptions = keyboardOptions,
        cursorBrush = SolidColor(colors.themeColor),
        onValueChange = onValueChange
    )
}