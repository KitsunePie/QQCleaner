package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.utils.dp2px

@Composable
fun Switch() {

    val enabled by remember { mutableStateOf(false) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val start = 0.dp2px()
    val end = 16.dp2px()
    Column(
        modifier = Modifier
            .width(36.dp)
            .height(20.dp)
            .clip(shape = RoundedCornerShape(100))
            .border(color = Color.Blue, width = 2.dp, shape = RoundedCornerShape(100))
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .absoluteOffset {
                    IntOffset(offset.x.toInt(), 0)
                }
                .clip(shape = RoundedCornerShape(percent = 100))
                .background(color = Color.Blue)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {

                        },
                        onDrag = { _: PointerInputChange, dragAmount: Offset ->
                            if ((offset.x > start &&
                                        offset.x < end) ||
                                ((offset.x < start && dragAmount.x > 0) ||
                                        (offset.x > end && dragAmount.x < 0))
                            ) {
                                offset += dragAmount
                            }
                        }

                    )
                }
        ) {

        }
    }

}