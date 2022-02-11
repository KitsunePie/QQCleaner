package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes
import me.kyuubiran.qqcleaner.ui.util.Shared

/**
 * 这里是修改类别的 UI
 */
@Composable
fun SortFixScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QQCleanerColorTheme.colors.pageBackgroundColor)
            .padding(top = QQCleanerData.statusBarHeight)
    ) {
        Column {
            TopBar(
                backClick = {
                    navController.popBackStack()
                },
                iconClick = {

                },
                titleText = Shared.currentEditCleanData.title,
                id = R.drawable.ic_save
            )
            // 这里判断是否为空
            if (true) {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 24.dp)
                        .background(
                            color = QQCleanerColorTheme.colors.appBarsAndItemBackgroundColor,
                            shape = QQCleanerShapes.cardGroupBackground
                        )
                ) {
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
                        text = "点击按钮添加路径",
                        style = QQCleanerTypes.EmptyTipStyle,
                        color = QQCleanerColorTheme.colors.thirdTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }
        }
    }
}