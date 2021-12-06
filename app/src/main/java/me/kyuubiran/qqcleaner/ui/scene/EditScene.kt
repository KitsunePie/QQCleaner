package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.QQCleanerViewModel
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.composable.dialog.ConfigDialog
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardGroupBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.TitleStyle

@Composable
fun EditScene(viewModel: QQCleanerViewModel, navController: NavController) {
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
            .padding(top = viewModel.statusBarHeight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_export_24),
                contentDescription = "返回",
                modifier = Modifier.clickable {
                    navController.popBackStack(navController.graph.startDestinationId, false)
                }
            )
            Text(style = TitleStyle, text = "编辑配置")
        }

        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(56.dp)
                .background(color = colors.background, shape = cardGroupBackground)
                .clip(cardGroupBackground)
                .clickable {
                    isEdit = true
                }
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                contentDescription = "返回"
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = TitleStyle, text = "添加配置"
            )
        }

        LazyColumn() {

        }


    }
}