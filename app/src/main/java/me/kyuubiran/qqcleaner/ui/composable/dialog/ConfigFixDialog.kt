package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.composable.Line
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme

@Composable
fun ConfigFixDialog(
    data: CleanData,
    navController: NavController,
    onDismissRequest: () -> Unit,
) {
    val state = remember { mutableStateOf(true) }
    var height by remember { mutableStateOf(464f) }
    BottomDialog(
        onDismissRequest = onDismissRequest,
        dialogHeight = height,
        dialogText = data.title,
        state = state
    ) {
        // 线条绘制
        Line(QQCleanerColorTheme.colors.dialogLineColor)
        ConfigItem(id = R.drawable.ic_open,
            text = "执行",
            onClick = {
                data.pushToExecutionQueue()
                state.value = false
            }
        )
        ConfigItem(
            id = R.drawable.ic_edit, text = "编辑",
            onClick = {
                state.value = false
                navController.navigate(QQCleanerApp.ConfigSpecify) {
                    popUpTo(QQCleanerApp.Edit)
                }
            }
        )
        ConfigItem(
            id = R.drawable.ic_save,
            text = "导出至 Download 目录",
            onClick = {
                state.value = false
            }
        )
        ConfigItem(
            id = R.drawable.ic_copy,
            text = "复制到剪贴版",
            onClick = {
                state.value = false
            }
        )
        ConfigItem(
            id = R.drawable.ic_delete,
            text = "删除", onClick = {
                height = 219f
            }
        )
        DialogButton(true, text = stringResource(id = R.string.cancel)) { state.value = false }
    }
}
