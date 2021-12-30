package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val state = remember { mutableStateOf(true) }
    var isDel by remember { mutableStateOf(false) }
    BottomDialog(
        onDismissRequest = onDismissRequest,
        dialogHeight = if (isDel) 219f else 464f,
        dialogText = if (isDel) "删除确定" else data.title,
        state = state
    ) {
        if (!isDel) {
            // 线条绘制
            Line(colors.dialogLineColor)
            ConfigItem(id = R.drawable.ic_open,
                text = stringResource(id = R.string.execute_this_config),
                onClick = {
                    data.pushToExecutionQueue()
                    state.value = false
                }
            )
            ConfigItem(id = R.drawable.ic_edit,
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
                    isDel = true
                }
            )
            DialogButton(true, text = stringResource(id = R.string.cancel)) {
                state.value = false
            }
        } else {
            Text(
                modifier = Modifier.padding(
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
                        .background(
                            colors.themeColor,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            isDel = false
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = colors.buttonTextColor,
                        style = itemTextStyle
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                        .height(48.dp)
                        .background(
                            colors.dialogButtonPress,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable {
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
