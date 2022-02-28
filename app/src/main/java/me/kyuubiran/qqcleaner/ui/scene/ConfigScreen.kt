package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.QQCleanerData.statusBarHeight
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.composable.Fab
import me.kyuubiran.qqcleaner.ui.composable.Switch
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.composable.dialog.ConfigAddDialog
import me.kyuubiran.qqcleaner.ui.composable.dialog.ConfigDialog
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardGroupBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.EmptyTipStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.TipStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.itemTextStyle
import me.kyuubiran.qqcleaner.util.CleanManager
import me.kyuubiran.qqcleaner.util.rememberMutableStateOf

/**
 * 这里是展示配置
 */
@Composable
fun ConfigScreen(navController: NavController) {
    // 配置文件列表
    val cfgList = remember {
        mutableStateListOf(*CleanManager.getAllConfigs())
    }

    // 新建配置对话框
    var canCreateNewConfigDialogShow by remember { mutableStateOf(false) }
    if (canCreateNewConfigDialogShow) {
        ConfigAddDialog(cfgList) {
            canCreateNewConfigDialogShow = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.pageBackgroundColor)
            .padding(top = statusBarHeight)
    ) {
        Column {
            TopBar(
                click = {
                    navController.popBackStack(navController.graph.startDestinationId, false)
                },
                stringResource(id = R.string.modify_config)
            )

            if (!cfgList.isEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp)
                        .background(
                            color = colors.appBarsAndItemBackgroundColor,
                            shape = cardGroupBackground
                        )
                ) {
                    items(cfgList.size) { idx ->
                        EditItem(
                            cfgList[idx], onRemove = {
                                cfgList.removeAt(idx)
                            }, navController
                        )
                    }
                }
            } else {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter =
                        if (QQCleanerData.isDark)
                            painterResource(id = R.drawable.ic_list_empty_dark)
                        else
                            painterResource(id = R.drawable.ic_list_empty),
                        contentDescription = stringResource(
                            id = R.string.list_empty
                        ), modifier = Modifier.size(96.dp)
                    )
                    Text(
                        text = "点击按钮添加配置",
                        style = EmptyTipStyle,
                        color = colors.thirdTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }
        }
        Fab(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = stringResource(id = R.string.add_config),
            onClick = {
                canCreateNewConfigDialogShow = true
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EditItem(data: CleanData, onRemove: (CleanData) -> Unit, navController: NavController) {
    val enable = rememberMutableStateOf(value = data.enable)

    var configFixDialogShow by remember { mutableStateOf(false) }
    if (configFixDialogShow) {
        ConfigDialog(data, onRemove, navController) {
            configFixDialogShow = false
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(shape = cardGroupBackground)
            .combinedClickable(
                onClick = {
                    enable.value = !enable.value
                    data.enable = enable.value
                    data.save()
                },
                onLongClick = {
                    configFixDialogShow = true
                })
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(text = data.title, style = itemTextStyle, color = colors.secondTextColor)
                    Text(
                        text = stringResource(id = R.string.config_author, data.author),
                        style = TipStyle,
                        color = colors.thirdTextColor
                    )
                }
            }
            Switch(checked = enable)
        }
    }
}
