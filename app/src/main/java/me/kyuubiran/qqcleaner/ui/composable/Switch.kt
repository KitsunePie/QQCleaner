package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.util.ColorUtils
import me.kyuubiran.qqcleaner.ui.util.dp2px
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Switch(
    checked: MutableState<Boolean>
) {
    val context = LocalContext.current

    fun Float.fixedRange(): Float {
        return when {
            this > 1f -> 1f
            this < 0f -> 0f
            else -> this
        }
    }

    fun Float.dp2px(): Float {
        return this.dp2px(context)
    }

    val swipeableState = rememberSwipeableState(
        // 初始值
        initialValue = if (checked.value) 1 else 0,
        // 动画曲线可以调节修改
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )
    // 通过 Boolean 反向给按钮赋值
    LaunchedEffect(checked.value) {
        swipeableState.animateTo(if (checked.value) 1 else 0)

    }

    // 按钮的宽高
    val width = 36.dp
    val height = 20.dp

    // 按钮球形的宽
    val squareSize = 10.dp
    val max = 14.dp

    val maxProgress = max.value.dp2px()
    // 按钮颜色，暂时没有更好的办法
    val color = Color(
        ColorUtils.mixColor(
            colors.toggleOffColor.toArgb(),
            colors.toggleOnColor.toArgb(),
            (swipeableState.offset.value / maxProgress).let {
                // 修复初始化是开但是颜色不变的情况
                if (it == 0f && checked.value)
                    1f
                else
                    it
            }.fixedRange()
        )
    )

    // 这个就是进度条的分配比例
    val anchors = mapOf(0f to 0, maxProgress to 1)
    // 通过按钮状态给 Boolean 赋值
    LaunchedEffect(swipeableState.offset.value) {
        // 如果他没有在运动
        if (((swipeableState.offset.value / maxProgress).fixedRange() == 1.0f) &&
            !swipeableState.isAnimationRunning
        ) {
            checked.value = true
        }
        // 防止值的修改错误
        if (((swipeableState.offset.value / maxProgress).fixedRange() == 0f) &&
            !swipeableState.isAnimationRunning
        )
            checked.value = false
    }

    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .clip(shape = RoundedCornerShape(100))
            // 描边
            .border(
                color = colors.toggleBorderColor,
                width = 2.dp,
                shape = RoundedCornerShape(100)
            )
            // 滑动动画
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            // 左右边距
            .padding(5.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            Modifier
                // 通过滑动的位置来修改球形的位置
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .width(squareSize)
                // 通过给高度乘系数完成转换
                .height((5 * (1 + swipeableState.offset.value / maxProgress)).dp)
                .clip(shape = RoundedCornerShape(percent = 100))
                .background(color)
        )
    }
}

@Composable
fun SwitchItem(
    text: String,
    checked: MutableState<Boolean>,
    onClick: ((Boolean) -> Unit)? = null,
    clickNoToggle: Boolean = false
) {
    fun toggle() {
        if (!clickNoToggle) {
            checked.value = !checked.value
            if (onClick != null) {
                onClick(checked.value)
            }
        }
    }

    Item(text = text, onClick = { toggle() }) {
        Switch(checked = checked)
    }
}
