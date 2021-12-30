package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Line(lineColor: Color) {
    Canvas(
        modifier = Modifier
            .padding(top = 4.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawRect(
            color = lineColor,
            size = this.size
        )
    }
}