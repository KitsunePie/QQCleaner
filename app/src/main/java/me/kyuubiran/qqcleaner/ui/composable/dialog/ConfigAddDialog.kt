package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.annotation.DrawableRes
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
import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logeIfThrow
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.R.string.cancel
import me.kyuubiran.qqcleaner.R.string.dialog_title_config
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.composable.Line
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.dialogButtonBackground


@Composable
fun ConfigAddDialog(
    list: SnapshotStateList<CleanData>,
    onDismissRequest: () -> Unit,
) {
    val state = remember { mutableStateOf(true) }
    BottomDialog(
        onDismissRequest = onDismissRequest,
        dialogHeight = if (list.isEmpty()) 408f else 352f,
        dialogText = stringResource(id = dialog_title_config),
        state = state
    ) {
        // 线条绘制
        Line(
            Modifier
                .padding(top = 4.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(1.dp)
        )

        ConfigItem(
            id = R.drawable.ic_cilpboard,
            text = stringResource(id = R.string.import_from_clipboard),
            onClick = {
                runCatching {
                    CleanData.fromClipboard()!!.let {
                        list.add(it)
                        it.save()
                        Log.toast(
                            moduleRes.getString(
                                R.string.import_from_clipboard_success,
                                it.title
                            )
                        )
                    }
                }.logeIfThrow {
                    Log.toast(moduleRes.getString(R.string.import_from_clipboard_error))
                }
                state.value = false
            }
        )
        ConfigItem(
            id = R.drawable.ic_file,
            text = stringResource(id = R.string.import_from_file),
            onClick = {
                state.value = false
            }
        )
        ConfigItem(
            id = R.drawable.ic_add,
            text = stringResource(id = R.string.create_config),
            onClick = {
                state.value = false
            }
        )
        if (list.isEmpty()) {
            ConfigItem(
                id = R.drawable.ic_default,
                text = stringResource(id = R.string.create_default_config),
                onClick = {
                    list.add(CleanData.createDefaultCleanData().also { it.save() })
                    Log.toast(moduleRes.getString(R.string.create_default_config_success))
                    state.value = false
                }
            )
        }
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
            tint = colors.secondTextColor
        )
        Text(
            text = text,
            color = colors.secondTextColor,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        )
    }
}
