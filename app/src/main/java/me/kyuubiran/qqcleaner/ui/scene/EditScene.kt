package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import me.kyuubiran.qqcleaner.ui.composable.Switch
import me.kyuubiran.qqcleaner.ui.composable.dialog.ConfigDialog
import me.kyuubiran.qqcleaner.ui.composable.dialog.ConfigFixDialog
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardGroupBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.TipStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.TitleStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.itemTextStyle
import me.kyuubiran.qqcleaner.util.rememberMutableStateOf

@Composable
fun EditScene(navController: NavController) {
    // 设置编辑文本
    var isEdit by remember { mutableStateOf(false) }
    if (isEdit) {
        ConfigDialog {
            isEdit = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.cardBackgroundColor)
            .padding(top = statusBarHeight)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back_black_24dp),
                contentDescription = "返回",
                modifier = Modifier.clickable {
                    navController.popBackStack(navController.graph.startDestinationId, true)
                },
                tint = colors.textColor
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                style = TitleStyle,
                text = "编辑配置",
                color = colors.textColor
            )
        }

        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(56.dp)
                .clip(cardGroupBackground)
                .background(color = colors.background, shape = cardGroupBackground)
                .clickable {
                    isEdit = true
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_add),
                contentDescription = "修改",
                tint = colors.textColor
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = itemTextStyle,
                text = "添加配置",
                color = colors.textColor
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .background(color = colors.background, shape = cardGroupBackground)
        ) {
            this.item {
                EditItem(navController = navController)
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EditItem(text: String = "配置名字", name: String = "作者", navController: NavController) {
    val enable = rememberMutableStateOf(value = false)

    var configFixDialogShow by remember { mutableStateOf(false) }
    if (configFixDialogShow) {
        ConfigFixDialog(text, navController) {
            configFixDialogShow = false
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(shape = cardGroupBackground)
            .combinedClickable(
                onClick = { enable.value = !enable.value },
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
                    Text(text = text, style = itemTextStyle, color = colors.textColor)
                    Text(text = name, style = TipStyle, color = colors.textColor.copy(alpha = 0.8f))
                }
            }
            Switch(checked = enable)
        }
    }
}
