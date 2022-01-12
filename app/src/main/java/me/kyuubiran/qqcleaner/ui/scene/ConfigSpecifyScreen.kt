package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.QQCleanerData.statusBarHeight
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.QQCleanerApp.ConfigFix
import me.kyuubiran.qqcleaner.ui.composable.Switch
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.composable.dialog.EditConfigDialog
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardGroupBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes
import me.kyuubiran.qqcleaner.util.rememberMutableStateOf

@Composable
fun ConfigSpecifyScreen(navController: NavController) {
    val enable = rememberMutableStateOf(false)
    var editDialogShow by remember { mutableStateOf(false) }
    if (editDialogShow) {
        EditConfigDialog(onDismissRequest = { text ->
            editDialogShow = false
            // 其他
        }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.cardBackgroundColor)
            .padding(top = statusBarHeight)
            .focusable(true)
    ) {
        TopBar(
            {
                navController.popBackStack(QQCleanerApp.Edit, false)
            },
            // 这里没什么用，到时候直接填入那个对应的配置名就可以
            titleText = "配置名字",
            id = R.drawable.ic_config_edit
        )

        Row(
            modifier = Modifier
                .padding(24.dp)
                .height(56.dp)
                .fillMaxWidth()
                .clickable { enable.value = !enable.value }
                .background(color = colors.themeColor, shape = cardGroupBackground)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        "启用配置",
                        style = QQCleanerTypes.itemTextStyle,
                        color = colors.buttonTextColor
                    )
                }
                Switch(checked = enable)
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .background(color = colors.background, shape = cardGroupBackground)
        ) {
            this.item {
                ConfigItem(
                    onClick = {
                        navController.navigate(ConfigFix)
                    }
                )
            }
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
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                tint = colors.textColor,
                contentDescription = "修改"
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = QQCleanerTypes.itemTextStyle, text = "添加类别",
                color = colors.textColor,
            )
        }
    }
}

@Composable
fun ConfigItem(text: String = "配置名字", onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(shape = cardGroupBackground)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        Column {
            Text(text = text, style = QQCleanerTypes.itemTextStyle, color = colors.textColor)
        }
    }
}