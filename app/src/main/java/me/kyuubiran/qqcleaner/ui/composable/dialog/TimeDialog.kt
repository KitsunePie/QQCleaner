package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK
import android.view.inputmethod.InputMethodManager
import androidx.compose.animation.animateColorAsState
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


@Composable
fun TimeDialog(
    onDismissRequest: (String) -> Unit,
) {

    val context = LocalContext.current as Activity
    var text by remember { mutableStateOf("") }
    val state = remember { mutableStateOf(true) }

    val isSoftShowing = remember { mutableStateOf(true) }
    EditBottomDialog(
        onDismissRequest = {
            onDismissRequest(text)
        },
        dialogHeight = 240f,
        state = state,
        isSoftShowing = isSoftShowing,
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
                            if (!isSoftShowing.value)
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


