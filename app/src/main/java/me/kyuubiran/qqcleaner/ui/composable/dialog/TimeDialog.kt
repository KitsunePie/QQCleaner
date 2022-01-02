package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.R
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK
import android.view.WindowInsets
import android.view.WindowInsets.Type.ime
import android.view.WindowInsetsAnimation
import android.view.inputmethod.InputMethodManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R.string.dialog_title_time
import me.kyuubiran.qqcleaner.R.string.set_auto_clean_interval_desc
import me.kyuubiran.qqcleaner.ui.composable.TextField
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.dialogEditBackGround
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.DialogEditStyle
import me.kyuubiran.qqcleaner.ui.utils.px2dp


@Composable
fun TimeDialog(
    onDismissRequest: (String) -> Unit,
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

    var text by remember { mutableStateOf("") }
    context.window.decorView.apply {
        if (SDK_INT >= R) {
            val callback =
                object : WindowInsetsAnimation.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
                    // 键盘抬升的时候，键盘的高变化
                    override fun onProgress(
                        insets: WindowInsets,
                        animations: MutableList<WindowInsetsAnimation>
                    ): WindowInsets {
                        softKeyboardHeight = insets.getInsets(ime()).bottom.px2dp()
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
    if (SDK_INT < R)
        LaunchedEffect(hasKeyboard) {
            bottomHeight.animateTo(
                targetValue = if (hasKeyboard) softKeyboardHeight else 0f,
                animationSpec = tween(300)
            )
        }
    val state = remember { mutableStateOf(true) }
    BottomDialog(
        onDismissRequest = {
            onDismissRequest(text)
        },
        dialogHeight = 240f,
        state = state,
        // 为啥卡顿我也不清楚，但是我猜想跟这里有关系？
        // 需要计算好多次
        bottomHeight = if (SDK_INT >= R)
            softKeyboardHeight.dp
        else
            bottomHeight.value.dp,
        dialogText = stringResource(id = dialog_title_time)
    ) {
        Box(
            // 这是居中，但是没什么必要
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp)
                .background(
                    color = colors.dialogEditColor,
                    shape = dialogEditBackGround
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
        ) {
            TextField(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .onKeyEvent {
                        // 因为输入的时候焦点会聚集在 输入框，所以输入框的需要进行是否为返回事件的判断
                        // 实际上还是保留了类似早期 android 及实体按键的东西
                        // 返回是一个按键
                        if (it.nativeKeyEvent.action == ACTION_UP
                            && it.nativeKeyEvent.keyCode == KEYCODE_BACK
                        ) {
                            if (softKeyboardHeight == 0f)
                                state.value = false
                            return@onKeyEvent true
                        }
                        false
                    },
                value = text,
                // 这是调用的数字键盘
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                // 这是对输入的过滤，文本为纯数字
                onValueChange = { value ->
                    text = value.filter {
                        it.isDigit()
                    }
                }
            )
            val editHintColor by animateColorAsState(
                if (text.isEmpty())
                    colors.dialogEditHintColor else colors.transparent,
                tween(100)
            )

            Text(
                text = stringResource(id = set_auto_clean_interval_desc),
                color = editHintColor,
                style = DialogEditStyle,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
            )
        }
        // 判断是否为空，为空的时候无法点击不为空的时候，可以点击
        DialogButton(text.isNotEmpty()) {
            // 需要获取点击之后的内容，text 就可以啦
            state.value = false
            // 这个是收回输入框
            val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(context.window.decorView.windowToken, 0)
        }

    }
}

