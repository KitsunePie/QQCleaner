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
    onRemove: (CleanData) -> Unit,
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
            text = stringResource(id = R.string.execute_this_config),
            onClick = {
                data.pushToExecutionQueue()
                state.value = false
            }
        )
        ConfigItem(id = R.drawable.ic_edit, text = stringResource(id = R.string.modify_config),
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
                onRemove(data)
                data.delete()
                height = 219f
            }
        )
        DialogButton(true, text = stringResource(id = R.string.cancel)) { state.value = false }
    }
}
