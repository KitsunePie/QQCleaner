package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.composable.EditText

@Composable
fun EditConfigDialog(onDismissRequest: (String) -> Unit) {
    // todo 提取文本到 R.string

    val context = LocalContext.current as Activity
    val state = remember { mutableStateOf(true) }
    val text = remember { mutableStateOf("") }
    val isSoftShowing = remember { mutableStateOf(true) }
    EditBottomDialog(
        onDismissRequest = {
            onDismissRequest(text.value)
        },
        dialogHeight = 312f,
        state = state,
        isSoftShowing = isSoftShowing,
        dialogText = stringResource(id = R.string.dialog_title_edit_config)
    ) {
        val name = remember { mutableStateOf("") }
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp),
            text = name,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { value ->
                name.value = value.filter {
                    it.isDigit()
                }
            },
            onKeyEvent = {
                // 因为输入的时候焦点会聚集在 输入框，所以输入框的需要进行是否为返回事件的判断
                // 实际上还是保留了类似早期 android 及实体按键的东西
                // 返回是一个按键
                if (it.nativeKeyEvent.action == KeyEvent.ACTION_UP
                    && it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_BACK
                ) {
                    if (!isSoftShowing.value)
                        state.value = false
                    return@EditText true
                }
                false
            },
            hintText = "配置名称"
        )

        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 24.dp)
                .height(56.dp),
            text = text,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { value ->
                text.value = value.filter {
                    it.isDigit()
                }
            },
            onKeyEvent = {
                // 因为输入的时候焦点会聚集在 输入框，所以输入框的需要进行是否为返回事件的判断
                // 实际上还是保留了类似早期 android 及实体按键的东西
                // 返回是一个按键
                if (it.nativeKeyEvent.action == KeyEvent.ACTION_UP
                    && it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_BACK
                ) {
                    if (!isSoftShowing.value)
                        state.value = false
                    return@EditText true
                }
                false
            },
            hintText = "作者名称"
        )

        // 判断是否为空，为空的时候无法点击不为空的时候，可以点击
        DialogButton(text.value.isNotEmpty()) {
            // 需要获取点击之后的内容，text 就可以啦
            state.value = false
            // 这个是收回输入框
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(context.window.decorView.windowToken, 0)
        }
    }
}