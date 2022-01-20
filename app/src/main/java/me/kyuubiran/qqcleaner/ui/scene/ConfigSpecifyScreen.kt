package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.QQCleanerData.statusBarHeight
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.composable.SwitchItem
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.composable.dialog.EditConfigDialog
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardGroupBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes
import me.kyuubiran.qqcleaner.ui.util.Shared
import me.kyuubiran.qqcleaner.util.rememberMutableStateOf

@Composable
fun ConfigSpecifyScreen(navController: NavController) {
    val enable = rememberMutableStateOf(Shared.currentEditCleanData.enable)
    var editDialogShow by remember { mutableStateOf(false) }
    if (editDialogShow) {
        EditConfigDialog(onDismissRequest = {
            editDialogShow = false
            // 其他
        })
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.cardBackgroundColor)
            .padding(top = statusBarHeight)
            .focusable(true)
    ) {
        TopBar(
            backClick = {
                navController.popBackStack()
            },
            iconClick = {
                Shared.currentEditCleanData.save()
                Log.toast(
                    appContext.getString(
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
//                .background(color = colors.themeColor, shape = cardGroupBackground)
                .background(color = colors.background, shape = cardGroupBackground)
                .padding(start = 16.dp, end = 16.dp)
        ) {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Box(
//                    modifier = Modifier.weight(1f),
//                    contentAlignment = Alignment.CenterStart
//                ) {
//                    Text(
//                        stringResource(id = R.string.enable_config),
//                        style = QQCleanerTypes.itemTextStyle,
//                        color = colors.buttonTextColor
//                    )
//                }
//                Switch(checked = enable,)
//            }
            SwitchItem(
                text = stringResource(id = R.string.enable_config),
                checked = enable,
                onClick = {
                    Shared.currentEditCleanData.enable = it
                })
        }

        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(56.dp)
                .background(color = colors.background, shape = cardGroupBackground)
                .clip(cardGroupBackground)
                .clickable {
                    editDialogShow = true
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                tint = colors.textColor,
                contentDescription = "修改信息"
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = QQCleanerTypes.itemTextStyle,
                text = stringResource(id = R.string.edit_info),
                color = colors.textColor,
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .background(color = colors.background, shape = cardGroupBackground)
        ) {
            items(Shared.currentEditCleanData.content) { item ->
                ConfigItem(data = item)
            }
        }
    }
}

@Composable
fun ConfigItem(data: CleanData.PathData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(shape = cardGroupBackground)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        SwitchItem(
            text = data.title,
            checked = rememberMutableStateOf(value = data.enable),
            onClick = {
                data.enable = it
            })
    }
}