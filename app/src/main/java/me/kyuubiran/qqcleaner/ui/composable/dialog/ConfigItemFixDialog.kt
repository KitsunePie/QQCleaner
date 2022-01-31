package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R.string.dialog_title_time
import me.kyuubiran.qqcleaner.ui.composable.EditText
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.ConfigItemFixStyle
import me.kyuubiran.qqcleaner.ui.util.hideKeyBoard

@Composable
fun ConfigItemFixDialog(
    onDismissRequest: (String) -> Unit,
) {

    val context = LocalContext.current as Activity
    val text = remember { mutableStateOf("") }
    val state = remember { mutableStateOf(true) }

    val isSoftShowing = remember { mutableStateOf(true) }
    EditBottomDialog(
        onDismissRequest = {
            onDismissRequest(text.value)
        },
        dialogHeight = 308f,
        state = state,
        isSoftShowing = isSoftShowing,
        dialogText = stringResource(id = dialog_title_time)
    ) {
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(80.dp),
            text = text,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { value ->
                text.value = value.filter {
                    it.isDigit()
                }
            },
            onKeyEvent = {
                // 因为输入的时候焦点会聚集在 输入框，所以输入框的需要进行是否为返回事件的判断
                // 实际上还是保留了类似早期 android 及实体按键的东西
                // 返回是一个按键
                if (it.nativeKeyEvent.action == ACTION_UP
                    && it.nativeKeyEvent.keyCode == KEYCODE_BACK
                ) {
                    if (!isSoftShowing.value)
                        state.value = false
                    return@EditText true
                }
                false
            },
            // todo 测试内容，所以需要咕咕咕
            hintText = "/storage/emulated/0/Android/data/com.tencent.mobileqq/caches"
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 24.dp)
                .padding(horizontal = 24.dp)
                .height(28.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .background(
                        shape = QQCleanerShapes.dialogConfigItemBackground,
                        color = colors.themeColor
                    )
                    .height(28.dp)
                    .width(84.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "共有目录", style = ConfigItemFixStyle, color = colors.buttonTextColor)
            }
            Row(
                modifier = Modifier
                    .background(
                        shape = QQCleanerShapes.dialogConfigItemBackground,
                        color = colors.themeColor
                    )
                    .height(28.dp)
                    .width(84.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "私有目录", style = ConfigItemFixStyle, color = colors.buttonTextColor)
            }
            Row(
                modifier = Modifier
                    .background(
                        shape = QQCleanerShapes.dialogConfigItemBackground,
                        color = colors.themeColor
                    )
                    .height(28.dp)
                    .width(84.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Tencent 目录",
                    style = ConfigItemFixStyle,
                    color = colors.buttonTextColor
                )
            }
        }
        // 判断是否为空，为空的时候无法点击不为空的时候，可以点击
        DialogButton(text.value.isNotEmpty()) {
            // 需要获取点击之后的内容，text 就可以啦
            state.value = false
            // 这个是收回输入框
            context.hideKeyBoard()
        }
    }
}


