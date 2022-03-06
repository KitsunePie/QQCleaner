package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.composable.Switch
import me.kyuubiran.qqcleaner.ui.composable.SwitchItem
import me.kyuubiran.qqcleaner.ui.composable.TopBar
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
//    var sortAddDialogShow by remember { mutableStateOf(false) }
//    if (sortAddDialogShow) {
//        SortAddDialog()
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.pageBackgroundColor)
            .statusBarsPadding()
    ) {
        Column {
            TopBar(
                backClick = {
                    //TODO("切换配置文件开关 外层状态并不会更新")
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
                        color = if (enable.value)
                            colors.mainThemeColor else colors.appBarsAndItemBackgroundColor,
                        shape = cardGroupBackground
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(cardGroupBackground)
                        .clickable {
                            enable.value = !enable.value
                            Shared.currentEditCleanData.enable = enable.value
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.enable_config),
                        style = QQCleanerTypes.itemTextStyle,
                        color = if (enable.value) colors.whiteColor else colors.secondTextColor,
                        modifier = Modifier.weight(1f),
                    )
                    Switch(
                        checked = enable,
                        switchBorderColor = if (enable.value)
                            colors.whiteColor else colors.switchLineColor,
                        switchOffColor = if (enable.value)
                            colors.whiteColor else colors.switchLineColor,
                        switchOnColor = if (enable.value)
                            colors.whiteColor else colors.switchLineColor,
                    )
                }
            }
            // 这里是列表的判断
            if (Shared.currentEditCleanData.content.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp)
                        .background(
                            color = colors.appBarsAndItemBackgroundColor,
                            shape = cardGroupBackground
                        ),
                    contentPadding = rememberInsetsPaddingValues(LocalWindowInsets.current.navigationBars)
                ) {
                    items(Shared.currentEditCleanData.content) { item ->
//                        var sortDialogShow by remember { mutableStateOf(false) }
//                        if (sortDialogShow) {
//                            SortDialog(
//                                navController = navController,
//                                data = item,
//                            ) {
//                                sortDialogShow = false
//                            }
//                        }

                        SortItem(data = item, onLongClick = {
//                            sortDialogShow = true
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
                        text = stringResource(id = R.string.empty),
                        style = QQCleanerTypes.EmptyTipStyle,
                        color = colors.thirdTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }
        }

//        Fab(
//            modifier = Modifier
//                .align(Alignment.BottomCenter),
//            text = stringResource(id = R.string.add_sort),
//            onClick = {
//                sortAddDialogShow = true
//            }
//        )
    }
}

@Composable
fun SortItem(data: CleanData.PathData, onLongClick: () -> Unit = {}) {
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