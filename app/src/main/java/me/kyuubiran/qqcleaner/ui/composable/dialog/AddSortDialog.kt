package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.view.KeyEvent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.composable.EditText
import me.kyuubiran.qqcleaner.ui.composable.Line
import me.kyuubiran.qqcleaner.ui.composable.dialog.SortDialogScreen.*
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes
import me.kyuubiran.qqcleaner.ui.util.RippleCustomTheme
import me.kyuubiran.qqcleaner.ui.util.hideKeyBoard

private enum class SortDialogScreen {
    Del,
    Edit,
    Main
}

@Composable
fun SortAddDialog(
    navController: NavController,
    onDismissRequest: () -> Unit
) {
    val state = remember { mutableStateOf(true) }
    val isSoftShowing = remember { mutableStateOf(true) }
    val isDialogScreen = remember { mutableStateOf(Main) }
    EditBottomDialog(
        onDismissRequest = onDismissRequest,
        dialogHeight = when (isDialogScreen.value) {
            Main -> 352f
            Edit -> 240f
            Del -> 219f
        },
        dialogText = when (isDialogScreen.value) {
            Main -> "名字"
            Edit -> stringResource(id = R.string.dialog_title_edit_config)
            Del -> stringResource(id = R.string.dialog_del_title)
        },
        isSoftShowing = isSoftShowing,
        state = state
    ) {
        Box {
            Crossfade(targetState = isDialogScreen.value) { screen ->
                when (screen) {
                    Main -> ConfigUI(
                        state = state,
                        navController = navController,
                        screen = isDialogScreen
                    )
                    Edit -> EditUI(
                        state = state,
                        isSoftShowing = isSoftShowing
                    )
                    Del -> DelUI(
                        state = state,
                        screen = isDialogScreen,
                    )
                }
            }
        }
    }
}

@Composable
private fun ConfigUI(
    state: MutableState<Boolean>,
    navController: NavController,
    screen: MutableState<SortDialogScreen>
) {
    Column {
        // 线条绘制
        Line(
            Modifier
                .padding(top = 4.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
        )
        ConfigItem(
            id = R.drawable.ic_edit,
            text = stringResource(id = R.string.modify_config),
            onClick = {
                state.value = false
                navController.navigate(
                    QQCleanerApp.Config,
                )
            }
        )
        ConfigItem(
            id = R.drawable.ic_edit_name,
            text = stringResource(id = R.string.modify_config_name),
            onClick = {
                screen.value = Edit
            }
        )
        ConfigItem(
            id = R.drawable.ic_delete,
            text = stringResource(id = R.string.delete_this_config), onClick = {
                screen.value = Del
            }
        )
        DialogButton(true, text = stringResource(id = R.string.cancel)) {
            state.value = false
        }
    }
}

@Composable
private fun EditUI(
    state: MutableState<Boolean>,
    isSoftShowing: MutableState<Boolean>
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

@Composable
private fun DelUI(
    state: MutableState<Boolean>,
    screen: MutableState<SortDialogScreen>,
) {
    Column {
        Text(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp
                ),
            text = "您确定要删除「」吗？",
            style = QQCleanerTypes.itemTextStyle,
            color = colors.secondTextColor
        )
        Row(modifier = Modifier.padding(24.dp)) {
            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colors.mainThemeColor)
                    .clickable {
                        screen.value = Main
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = colors.whiteColor,
                    style = QQCleanerTypes.itemTextStyle
                )
            }
            CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme(color = colors.fourPercentThemeColor)) {
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colors.fourPercentThemeColor)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            state.value = false
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        color = colors.mainThemeColor,
                        style = QQCleanerTypes.itemTextStyle
                    )
                }
            }
        }
    }
}