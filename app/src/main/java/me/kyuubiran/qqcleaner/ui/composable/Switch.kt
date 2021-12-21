package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.utils.dp2px
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun Switch(
    checked: MutableState<Boolean>,
    onCheckedChange: ((Boolean) -> Unit)? = null
) {
    val swipeableState = rememberSwipeableState(if (checked.value) 1 else 0)

    LaunchedEffect(checked.value) {
        swipeableState.animateTo(if (checked.value) 1 else 0)
    }

    val width = 36.dp
    val height = 20.dp
    val squareSize = 10.dp
    val max = 14.dp

    val sizePx = with(LocalDensity.current) { max.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1)
    val color = Color(
        mixColor(
            colors.toggleOffColor.toArgb(),
            colors.toggleOnColor.toArgb(),
            (swipeableState.offset.value / 14.dp2px()).let {
                if (it == 0f && checked.value)
                    1f
                else
                    it
            }.fixedRange()
        )
    )

    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .clip(shape = RoundedCornerShape(100))
            .border(
                color = colors.toggleBorderColor,
                width = 2.dp,
                shape = RoundedCornerShape(100)
            )
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .padding(5.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .width(squareSize)
                .height((5 * (1 + swipeableState.offset.value / 14.dp2px())).dp)
                .clip(shape = RoundedCornerShape(percent = 100))
                .background(color)
        )
    }
}

private fun Float.fixedRange(): Float {
    return when {
        this > 1f -> 1f
        this < 0f -> 0f
        else -> this
    }
}

private fun mixColor(color1: Int, color2: Int, ratio: Float): Int {
    val inverse = 1 - ratio
    val a = (color1 ushr 24) * inverse + (color2 ushr 24) * ratio
    val r = (color1 shr 16 and 0xFF) * inverse + (color2 shr 16 and 0xFF) * ratio
    val g = (color1 shr 8 and 0xFF) * inverse + (color2 shr 8 and 0xFF) * ratio
    val b = (color1 and 0xFF) * inverse + (color2 and 0xFF) * ratio
    return a.toInt() shl 24 or (r.toInt() shl 16) or (g.toInt() shl 8) or b.toInt()
}

