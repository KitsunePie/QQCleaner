package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R.string.dialog_title_time
import me.kyuubiran.qqcleaner.ui.composable.EditText
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.ConfigItemFixStyle
import me.kyuubiran.qqcleaner.ui.util.RippleCustomTheme
import me.kyuubiran.qqcleaner.ui.util.hideKeyBoard

enum class FileName {
    Tencent,
    Private,
    Public
}

@Composable
fun FileDialog(
    onDismissRequest: (String) -> Unit,
) {

    val context = LocalContext.current as Activity
    val text = remember { mutableStateOf("") }
    val state = remember { mutableStateOf(true) }
    val isSoftShowing = remember { mutableStateOf(true) }

    var isFile by remember { mutableStateOf(FileName.Public) }

    @Composable
    fun FileItem(text: String, file: FileName) {

        CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme(color = if (isFile == file) colors.whiteColor else colors.fourPercentThemeColor)) {
            Row(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clip(shape = QQCleanerShapes.dialogConfigItemBackground)
                    .background(
                        color = if (isFile == file) colors.mainThemeColor else colors.fourPercentThemeColor
                    )
                    .height(28.dp)
                    .clickable {
                        isFile = file
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val color = colors.whiteColor
                AnimatedVisibility(
                    visible = isFile == file,
                    modifier = Modifier.padding(start = 16.dp, end = 6.dp),
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(3.dp)
                    ) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        drawCircle(
                            color = color,
                            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                            radius = size.minDimension
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(
                        start = if (isFile == file) 0.dp else 16.dp,
                        end = 16.dp
                    ),
                    text = text,
                    style = ConfigItemFixStyle,
                    color = if (isFile == file) colors.whiteColor else colors.mainThemeColor
                )
            }
        }
    }

    EditDialog(
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
            keyboardOptions = KeyboardOptions.Default,
            onValueChange = {
            },
            onKeyEvent = {
                // 因为输入的时候焦点会聚集在 输入框，所以输入框的需要进行是否为返回事件的判断
                // 实际上还是保留了类似早期 android 及实体按键的东西
                // 返回是一个按键
                if (this.nativeKeyEvent.action == ACTION_UP
                    && this.nativeKeyEvent.keyCode == KEYCODE_BACK
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
            FileItem(text = "公开目录", FileName.Public)
            FileItem(text = "私有目录", FileName.Private)
            FileItem(text = "Tencent 目录", FileName.Tencent)


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


