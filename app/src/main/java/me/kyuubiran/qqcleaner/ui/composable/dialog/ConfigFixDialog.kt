package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.composable.Line
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.itemTextStyle

@Composable
fun ConfigFixDialog(
    data: CleanData,
    onRemove: (CleanData) -> Unit,
    navController: NavController,
    onDismissRequest: () -> Unit,
) {
    // 控制 Dialog 的开关
    val state = remember { mutableStateOf(true) }
    val isDel = remember { mutableStateOf(false) }
    BottomDialog(
        onDismissRequest = onDismissRequest,
        dialogHeight = if (isDel.value) 219f else 464f,
        dialogText = if (isDel.value) "删除确定" else data.title,
        state = state
    ) {
        Box {
            Row {
                AnimatedVisibility(
                    visible = !isDel.value,
                    enter = expandHorizontally(animationSpec = tween(300, delayMillis = 300)),
                    exit = shrinkHorizontally(animationSpec = tween(300))
                ) {
                    ConfigUI(
                        state = state,
                        data = data,
                        navController = navController,
                        isDel = isDel
                    )
                }
            }
            Row {
                AnimatedVisibility(
                    visible = isDel.value,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 300)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    DelUI(
                        state = state,
                        data = data,
                        isDel = isDel,
                        onRemove = onRemove
                    )
                }
            }
        }
    }
}

@Composable
fun ConfigUI(
    state: MutableState<Boolean>,
    data: CleanData,
    navController: NavController,
    isDel: MutableState<Boolean>
) {
    Column {
        // 线条绘制
        Line(
            colors.dialogLineColor,
            Modifier
                .padding(top = 4.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
        )
        ConfigItem(id = R.drawable.ic_open,
            text = stringResource(id = R.string.execute_this_config),
            onClick = {
                data.pushToExecutionQueue()
                state.value = false
            }
        )
        ConfigItem(
            id = R.drawable.ic_edit,
            text = stringResource(id = R.string.modify_config),
            onClick = {
                state.value = false
                navController.navigate(QQCleanerApp.ConfigSpecify) {
                    popUpTo(QQCleanerApp.Edit)
                }
            }
        )
        ConfigItem(
            id = R.drawable.ic_save,
            text = stringResource(id = R.string.export_this_config),
            onClick = {
                data.export()
                state.value = false
            }
        )
        ConfigItem(
            id = R.drawable.ic_copy,
            text = stringResource(id = R.string.copy_this_into_clipboard_config),
            onClick = {
                data.copyToClipboard()
                state.value = false
            }
        )
        ConfigItem(
            id = R.drawable.ic_delete,
            text = stringResource(id = R.string.delete_this_config), onClick = {
                isDel.value = true
            }
        )
        DialogButton(true, text = stringResource(id = R.string.cancel)) {
            state.value = false
        }
    }
}

@Composable
fun DelUI(
    state: MutableState<Boolean>,
    data: CleanData,
    isDel: MutableState<Boolean>,
    onRemove: (CleanData) -> Unit
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
            text = "您确定要删除「${data.title}」吗？",
            style = itemTextStyle,
            color = colors.textColor.copy(0.8f)
        )
        Row(modifier = Modifier.padding(24.dp)) {
            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colors.themeColor)
                    .clickable {
                        isDel.value = false
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = colors.buttonTextColor,
                    style = itemTextStyle
                )
            }
            CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme) {
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colors.dialogButtonPress)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            state.value = false
                            onRemove(data)
                            data.delete()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        color = colors.dialogButtonTextPress,
                        style = itemTextStyle
                    )
                }
            }
        }
    }
}