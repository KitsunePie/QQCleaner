package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.composable.dialog.ConfigItemFixDialog
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes
import me.kyuubiran.qqcleaner.ui.util.Shared

@Composable
fun FixConfigScreen(navController: NavController) {
    var canCreateNewConfigDialogShow by remember { mutableStateOf(false) }
    if (canCreateNewConfigDialogShow) {
        ConfigItemFixDialog {
            canCreateNewConfigDialogShow = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QQCleanerColorTheme.colors.cardBackgroundColor)
            .padding(top = QQCleanerData.statusBarHeight)
    ) {
        TopBar(
            backClick = {
                navController.popBackStack()
            },
            iconClick = {
                navController.popBackStack(QQCleanerApp.ConfigSpecify, false)
            },
            // 这里没什么用，到时候直接填入那个对应的配置名就可以
            titleText = Shared.currentEditCleanPathData.title,
            id = R.drawable.ic_save
        )
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = QQCleanerColorTheme.colors.background,
                    shape = QQCleanerShapes.cardGroupBackground
                )
                .clip(QQCleanerShapes.cardGroupBackground)
                .clickable {
                    canCreateNewConfigDialogShow = true
                }
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                tint = QQCleanerColorTheme.colors.textColor,
                contentDescription = "添加路径"
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = QQCleanerTypes.itemTextStyle, text = stringResource(id = R.string.add_path),
                color = QQCleanerColorTheme.colors.textColor,
            )
        }
    }
}