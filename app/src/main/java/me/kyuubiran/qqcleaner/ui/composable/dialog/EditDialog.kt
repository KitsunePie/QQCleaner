package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.graphics.Rect
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.R
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.util.px2dp

@Composable
fun EditDialog(
    dialogHeight: Float,
    dialogText: String,
    isSoftShowing: MutableState<Boolean>,
    onDismissRequest: () -> Unit,
    state: MutableState<Boolean>,
    content: @Composable () -> Unit,

    ) {
    val context = LocalContext.current as Activity
    var softKeyboardHeight by remember { mutableStateOf(0f) }
    val bottomHeight = remember { Animatable(0f) }
    var hasKeyboard by remember { mutableStateOf(false) }
    // 窗口的可见高度
    var windowHeight by remember { mutableStateOf(0) }

    fun Int.px2dp(): Float {
        return this.px2dp(context)
    }
    context.window.decorView.apply {
        if (SDK_INT >= R) {
            val callback =
                object : WindowInsetsAnimation.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
                    // 键盘抬升的时候，键盘的高变化
                    override fun onProgress(
                        insets: WindowInsets,
                        animations: MutableList<WindowInsetsAnimation>
                    ): WindowInsets {
                        softKeyboardHeight =
                            insets.getInsets(WindowInsets.Type.ime()).bottom.px2dp()
                        return insets
                    }
                }
            this.setWindowInsetsAnimationCallback(callback)
        } else {
            this.viewTreeObserver.addOnGlobalLayoutListener {
                val r = Rect()
                this.getWindowVisibleDisplayFrame(r)
                // 这个是当前窗口的可见高度
                val visibleHeight = r.height()
                if (windowHeight == 0) {
                    // 等于可见高度
                    windowHeight = visibleHeight
                } else {
                    softKeyboardHeight =
                        if (windowHeight == visibleHeight) {
                            hasKeyboard = false
                            0f
                        } else {
                            hasKeyboard = true
                            (windowHeight - visibleHeight).px2dp()
                        }
                }
            }
        }
    }
    LaunchedEffect(softKeyboardHeight) {
        isSoftShowing.value = softKeyboardHeight > 0
    }
    // todo 等待分析
    if (SDK_INT < R)
        LaunchedEffect(hasKeyboard) {
            bottomHeight.animateTo(
                targetValue = if (hasKeyboard) softKeyboardHeight else 0f,
                animationSpec = tween(300)
            )
        }
    BottomDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        dialogHeight = dialogHeight,
        state = state,
        // 为啥卡顿我也不清楚，但是我猜想跟这里有关系？
        // 需要计算好多次
        bottomHeight = if (SDK_INT >= R)
            softKeyboardHeight.dp
        else
            bottomHeight.value.dp,
        dialogText = dialogText
    ) {
        content()
    }
}