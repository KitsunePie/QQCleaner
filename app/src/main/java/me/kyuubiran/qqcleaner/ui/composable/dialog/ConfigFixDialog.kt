package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme

@Composable
fun ConfigFixDialog(
    name: String,
    navController: NavController,
    onDismissRequest: () -> Unit,
) {
    val state = remember { mutableStateOf(true) }
    BottomDialog(
        onDismissRequest = onDismissRequest,
        dialogHeight = 464f,
        dialogText = name,
        state = state
    ) {
        val lineColor = QQCleanerColorTheme.colors.dialogLineColor
        // 这个是线条的绘制，我实在不明白为啥要写的这么麻烦，等等修它
        Canvas(
            modifier = Modifier
                .padding(top = 4.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
        ) {
            drawRect(
                color = lineColor,
                size = this.size
            )
        }
        ConfigItem(id = R.drawable.ic_open,
            text = "执行",
            onClick = {
                state.value = false
            }
        )
        ConfigItem(id = R.drawable.ic_edit, text = "编辑",
            onClick = {
                navController.navigate(QQCleanerApp.ConfigSpecify) {
                    popUpTo(QQCleanerApp.Edit)
                }
                state.value = false
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
                state.value = false
            }
        )
        DialogButton(true, text = stringResource(id = R.string.cancel)) { state.value = false }
    }
}
