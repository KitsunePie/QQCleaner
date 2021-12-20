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
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.utils.dp2px

@Composable
fun Switch(
    checked: Boolean
) {
    val min = 0.dp2px()
    val median = 8.dp2px()
    val max = 16.dp2px()

    fun Float.fixedRange(): Int {
        return when {
            this > max -> max.toInt()
            this < min -> min.toInt()
            else -> this.toInt()
        }
    }

    var offset by remember {
        mutableStateOf(
            if (checked)
                Offset(max, 0f)
            else Offset(min, 0f)
        )
    }

    val progress = (offset.x.fixedRange() / max)

    Column(
        modifier = Modifier
            .width(36.dp)
            .height(20.dp)
            .clip(shape = RoundedCornerShape(100))
            .border(color = Color.Blue, width = 2.dp, shape = RoundedCornerShape(100))
            .padding(5.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                // 位置
                .absoluteOffset {
                    IntOffset(offset.x.fixedRange(), 0)
                }
                // 宽度
                .width(10.dp)
                .height((5 * (1 + progress)).dp)
                .clip(shape = RoundedCornerShape(percent = 100))
                .background(color = Color.Blue)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            offset = if (offset.x < median) {
                                Offset(min, 0f)
                            } else {
                                Offset(max, 0f)
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consumeAllChanges()
                            if ((offset.x in min..max) ||
                                ((offset.x <= min && dragAmount.x > 0) ||
                                        (offset.x >= max && dragAmount.x < 0))
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

