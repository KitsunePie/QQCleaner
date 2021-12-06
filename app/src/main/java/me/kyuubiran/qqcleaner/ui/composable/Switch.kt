package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun Switch() {
    var enabled by remember { mutableStateOf(false) }

    Canvas(modifier = Modifier
        .clickable { enabled = !enabled }
        .width(36.dp)
        .height(18.dp)) {

        drawRoundRect(
            color = Color.Blue,
            size = size,
            style = Stroke(width = 6.dp.value),
            cornerRadius = CornerRadius(size.height, size.height),
        )

        drawCircle(
            color = Color.Blue,
            center = Offset(x = (size.width / 3) * 2, y = size.height / 2),
            radius = 14.dp.value

        )
    }

}