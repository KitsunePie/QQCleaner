package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.QQCleanerData.statusBarHeight
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.composable.Fab
import me.kyuubiran.qqcleaner.ui.composable.SwitchItem
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.composable.dialog.SortDialog
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardGroupBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes
import me.kyuubiran.qqcleaner.ui.util.Shared
import me.kyuubiran.qqcleaner.util.rememberMutableStateOf

/**
 * 这里是展示类别
 */
@Composable
fun SortScreen(navController: NavController) {
    val enable = rememberMutableStateOf(Shared.currentEditCleanData.enable)

    var sortDialogShow by remember { mutableStateOf(false) }
    if (sortDialogShow) {
        SortDialog(
            navController = navController,
        ) {
            sortDialogShow = false
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
                backClick = {
                    navController.popBackStack()
                },
                iconClick = {
                    Shared.currentEditCleanData.save()
                    Log.toast(
                        moduleRes.getString(
                            R.string.config_saved,
                            Shared.currentEditCleanData.title
                        )
                    )
                },
                titleText = Shared.currentEditCleanData.title,
                id = R.drawable.ic_save
            )

            Row(
                modifier = Modifier
                    .padding(start = 24.dp, top = 24.dp, end = 24.dp)
                    .height(56.dp)
                    .fillMaxWidth()
                    .clickable { enable.value = !enable.value }
                    .background(
                        color = colors.appBarsAndItemBackgroundColor,
                        shape = cardGroupBackground
                    )
            ) {
                SwitchItem(
                    text = stringResource(id = R.string.enable_config),
                    checked = enable,
                    onClick = {
                        Shared.currentEditCleanData.enable = it
                    }
                )
            }
            // 这里是列表的判断
            if (true) {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 108.dp)
                        .background(
                            color = colors.appBarsAndItemBackgroundColor,
                            shape = cardGroupBackground
                        )
                ) {
                    items(Shared.currentEditCleanData.content) { item ->
                        ConfigItem(data = item, onLongClick = {
                            sortDialogShow = true
                        })
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp, bottom = 120.dp)
                        .fillMaxSize()
                        .background(
                            color = colors.appBarsAndItemBackgroundColor,
                            shape = cardGroupBackground
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter =
                        if (QQCleanerData.isDark)
                            painterResource(id = R.drawable.ic_list_empty_dark)
                        else
                            painterResource(id = R.drawable.ic_list_empty),
                        contentDescription = stringResource(
                            id = R.string.list_empty
                        ),
                        modifier = Modifier.size(96.dp)
                    )
                    Text(
                        text = "点击按钮添加类别",
                        style = QQCleanerTypes.EmptyTipStyle,
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

            }
        )
    }
}

@Composable
fun ConfigItem(data: CleanData.PathData, onLongClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(shape = cardGroupBackground)

    ) {
        SwitchItem(
            text = data.title,
            checked = rememberMutableStateOf(value = data.enable),
            onClick = {
                data.enable = it
            },
            onLongClick = {
                onLongClick()
            }
        )
    }
}