package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors

@Composable
fun Line(modifier: Modifier) {
    val dividerColor = colors.dividerColor
    Canvas(
        modifier = modifier
    ) {
        drawRect(
            color = dividerColor,
            size = this.size
        )
    }
}