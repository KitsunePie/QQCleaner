package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.tryOrFalse
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.R.string.cancel
import me.kyuubiran.qqcleaner.R.string.dialog_title_config
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.dialogButtonBackground


@Composable
fun ConfigDialog(
    list: SnapshotStateList<CleanData>,
    onDismissRequest: () -> Unit,
) {
    val state = remember { mutableStateOf(true) }
    BottomDialog(
        onDismissRequest = onDismissRequest,
        dialogHeight = 352f,
        dialogText = stringResource(id = dialog_title_config),
        state = state
    ) {
        val lineColor = colors.dialogLineColor
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

        ConfigItem(id = R.drawable.ic_cilpboard,
            text = "从剪贴板导入",
            onClick = {
                if (tryOrFalse {
                        CleanData.fromClipboard()!!.let {
                            list.add(it)
                            it.save()
                        }
                    }) {
                    Log.toast(appContext.getString(R.string.import_from_clipboard_success))
                } else {
                    Log.toast(appContext.getString(R.string.import_from_clipboard_error))
                }
                state.value = false
            }
        )
        ConfigItem(
            id = R.drawable.ic_file,
            text = "选择本地文件",
            onClick = {
                state.value = false
            }
        )
        ConfigItem(id = R.drawable.ic_icon_add,
            text = "新建配置",
            onClick = {
                state.value = false
            }
        )
        DialogButton(true, text = stringResource(id = cancel)) { state.value = false }
    }
}

@Composable
fun ConfigItem(
    @DrawableRes id: Int,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .height(56.dp)
            .fillMaxWidth()
            .clip(dialogButtonBackground)
            .clickable(
                onClick = onClick
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = text + "的图标",
            tint = colors.textColor
        )
        Text(
            text = text,
            color = colors.textColor,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        )
    }
}
