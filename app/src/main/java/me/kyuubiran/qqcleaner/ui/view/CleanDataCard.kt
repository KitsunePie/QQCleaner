package me.kyuubiran.qqcleaner.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.theme.Blue200
import me.kyuubiran.qqcleaner.util.rememberMutableStateOf

@Composable
fun ConfirmDialog(
    title: String,
    text: String,
    showable: MutableState<Boolean>,
    outDialogShowable: MutableState<Boolean>,
    confirm: (() -> Unit)? = null
) {
    Dialog(
        title = title,
        text = text,
        confirmBtnTitle = R.string.confirm,
        confirm = {
            showable.value = false
            outDialogShowable.value = false
            confirm?.invoke()
        },
        dismissBtnString = R.string.wrong_click,
        dismiss = {
            showable.value = false
        },
        dismissRequest = {
            showable.value = false
        },
        showable = showable
    )
}

@Composable
fun CleanDataClickDialog(
    cleanData: CleanData,
    checked: MutableState<Boolean>,
    showable: MutableState<Boolean>
) {
    // 确认执行配置文件Dialog
    val confirmExecuteDialogShow = rememberMutableStateOf(value = false)
    ConfirmDialog(
        title = stringResource(id = R.string.notify),
        text = stringResource(id = R.string.execute_clean_dialog_content).format(cleanData.title),
        showable = confirmExecuteDialogShow,
        outDialogShowable = showable,
        confirm = {
//            CleanManager.executeCleanData(cleanData)
        }
    )

    // 确认删除配置文件Dialog
    val confirmDeleteDialogShow = rememberMutableStateOf(value = false)
    ConfirmDialog(
        title = stringResource(id = R.string.warn),
        text = stringResource(id = R.string.delete_config_dialog_content).format(cleanData.title),
        showable = confirmDeleteDialogShow,
        outDialogShowable = showable,
        confirm = {

        }
    )

    Dialog(
        title = stringResource(id = R.string.modify_config_dialog_title).format(cleanData.title),
        content = {
            Column(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                // 启用/禁用
                DialogClickableItem(
                    text = if (checked.value) R.string.disable_this_config else R.string.enable_this_config,
                    leftIcon = if (checked.value) R.drawable.ic_baseline_toggle_off_24 else R.drawable.ic_baseline_toggle_on_24,
                    onClick = {
                        cleanData.enable = !cleanData.enable
                        checked.value = !checked.value
                        showable.value = false
                    })
                // 运行
                DialogClickableItem(
                    text = R.string.execute_this_config,
                    leftIcon = R.drawable.ic_baseline_play_arrow_24,
                    onClick = {
                        confirmExecuteDialogShow.value = true
                    }
                )
                // 编辑
                DialogClickableItem(
                    text = R.string.modify_this_config,
                    leftIcon = R.drawable.ic_baseline_edit_24,
                    onClick = { showable.value = false }
                )
                // 导出
                DialogClickableItem(
                    text = R.string.export_this_config,
                    leftIcon = R.drawable.ic_baseline_export_24,
                    onClick = {
                        showable.value = false
                        try {
                            cleanData.export()
                            Log.toast(
                                appContext.getString(R.string.export_success)
                                    .format("${cleanData.title}.json")
                            )
                        } catch (e: Exception) {
                            Log.e(e)
                            Log.toast(appContext.getString(R.string.export_error))
                        }
                    }
                )
                // 删除
                DialogClickableItem(
                    text = R.string.delete_this_config,
                    leftIcon = R.drawable.ic_baseline_delete_24,
                    onClick = {
                        confirmDeleteDialogShow.value = true
                    }
                )
            }
        },
        showable = showable,
        dismissRequest = { showable.value = false }
    )
}

@Composable
fun CleanDataCard(cleanData: CleanData) {
    val checked = rememberMutableStateOf(value = cleanData.enable)
    val rememberData = rememberMutableStateOf(value = cleanData)
    val showable = rememberMutableStateOf(value = false)

    CleanDataClickDialog(cleanData = rememberData.value, checked = checked, showable = showable)

    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(80.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 30.dp)
            .clickable { showable.value = true },
        shape = RoundedCornerShape(13.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
                    .padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Surface {
                    Text(
                        text = cleanData.title,
                        fontSize = 22.sp
                    )
                }
                Text(
                    text = "作者:${cleanData.author}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxHeight(0.25f),
                contentAlignment = Alignment.Center
            ) {
                Switch(
                    checked = checked.value,
                    onCheckedChange = {
                        checked.value = it
                        cleanData.enable = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Blue200,
                        uncheckedTrackColor = Color.Gray
                    )
                )
            }
        }
    }
}


@Preview
@Composable
private fun View() {
    val cleanData = CleanData(
        """
            {
              "title": "默认配置",
              "author": "KyuubiRan",
              "enable": true,
              "hostApp": "QQ",
              "content": [
                {
                  "title": "缓存",
                  "regexp": false,
                  "enable": false,
                  "path": [
                    "!AndroidData/Caches1",
                    "!AndroidData/Caches2"
                  ]
                },
                {
                  "title": "日志",
                  "enable": true,
                  "regexp": true,
                  "path": [
                    "!AndroidData/Log[0-9]"
                  ]
                }
              ]
            }
        """.trimIndent()
    )

    val checked = rememberMutableStateOf(value = true)
    val showable = rememberMutableStateOf(value = true)
}