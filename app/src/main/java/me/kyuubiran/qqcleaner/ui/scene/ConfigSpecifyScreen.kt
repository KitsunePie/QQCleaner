package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.QQCleanerData.statusBarHeight
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardGroupBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes

@Composable
fun ConfigSpecifyScreen(navController: NavController) {
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
                painter = painterResource(id = R.drawable.ic_baseline_export_24),
                contentDescription = "返回",
                modifier = Modifier.clickable {
                    navController.popBackStack(navController.graph.startDestinationId, false)
                }
            )
            Text(style = QQCleanerTypes.TitleStyle, text = "配置名字")
        }

        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .background(color = colors.themeColor, shape = cardGroupBackground)
                .padding(horizontal = 16.dp, vertical = 18.dp)
        ) {
            Text("启用配置", color = colors.buttonTextColor)
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .background(color = colors.background, shape = cardGroupBackground)
        ) {
            this.item {
                ConfigItem(
                    onClick = {

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
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                contentDescription = "修改"
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                style = QQCleanerTypes.TitleStyle, text = "添加类别"
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
            Text(text = text)
        }
    }
}