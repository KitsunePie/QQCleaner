package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.R.drawable.*

/**
 * 一个奇奇怪怪的小按钮
 *
 * @param checked 开关状态
 * @param isWhite 是否是白色按钮
 */
@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun Switch(
    checked: MutableState<Boolean>,
    isWhite: Boolean = false
) {
    if (isWhite) {
        Crossfade(targetState = QQCleanerData.isDark, animationSpec = tween(600)) {
            val image =
                AnimatedImageVector.animatedVectorResource(if (it) switch_off_to_on_white_drak else switch_off_to_on_white)

            Image(
                painter = rememberAnimatedVectorPainter(image, checked.value),
                contentDescription = "按钮"
            )
        }
    } else {
        Crossfade(targetState = QQCleanerData.isDark, animationSpec = tween(600)) {
            val image =
                AnimatedImageVector.animatedVectorResource(if (it) switch_default_off_to_on_drak else switch_default_off_to_on)
            Image(
                painter = rememberAnimatedVectorPainter(image, checked.value),
                contentDescription = "按钮"
            )
        }

    }

}

@Composable
fun SwitchItem(
    text: String,
    checked: MutableState<Boolean>,
    onClick: ((Boolean) -> Unit)? = null,
    onLongClick: () -> Unit = {},
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

    Item(text = text, onClick = { toggle() }, onLongClick = onLongClick) {
        Switch(checked = checked)
    }
}
