package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Line(lineColor: Color, modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {
        drawRect(
            color = lineColor,
            size = this.size
        )
    }
}