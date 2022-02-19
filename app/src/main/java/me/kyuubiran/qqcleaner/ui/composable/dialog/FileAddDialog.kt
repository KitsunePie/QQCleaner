package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.composable.EditText
import me.kyuubiran.qqcleaner.ui.util.hideKeyBoard

@Composable
fun FileAddDialog(
    onDismissRequest: () -> Unit
) {
    val state = remember { mutableStateOf(true) }
    val isSoftShowing = remember { mutableStateOf(true) }
    EditDialog(
        onDismissRequest = onDismissRequest,
        dialogHeight = 240f,
        dialogText = "编辑路径",
        isSoftShowing = isSoftShowing,
        state = state
    ) {
        val context = LocalContext.current as Activity
        val text = remember { mutableStateOf("") }
        val name = remember { mutableStateOf("") }
        Column {

            EditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(56.dp),
                text = name,
                keyboardOptions = KeyboardOptions.Default,
                onValueChange = { value ->
                    name.value = value
                },
                onKeyEvent = {
                    // 因为输入的时候焦点会聚集在 输入框，所以输入框的需要进行是否为返回事件的判断
                    // 实际上还是保留了类似早期 android 及实体按键的东西
                    // 返回是一个按键
                    if (this.nativeKeyEvent.action == KeyEvent.ACTION_UP
                        && this.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_BACK
                    ) {
                        if (!isSoftShowing.value)
                            state.value = false
                        return@EditText true
                    }
                    false
                },
                hintText = "配置名称"
            )

            // 判断是否为空，为空的时候无法点击不为空的时候，可以点击
            DialogButton(text.value.isNotEmpty()) {
                // 需要获取点击之后的内容，text 就可以啦
                state.value = false
                // 这个是收回输入框
                context.hideKeyBoard()
            }
        }
    }
}