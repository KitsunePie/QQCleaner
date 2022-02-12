package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes

@Composable
fun DeveloperScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QQCleanerColorTheme.colors.pageBackgroundColor)
            .padding(top = QQCleanerData.statusBarHeight)
    ) {
        TopBar(
            click = {
                navController.popBackStack(navController.graph.startDestinationId, false)
            },
            stringResource(id = R.string.title_dev)
        )
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(24.dp)
        )
        CardGroup(72.dp) {
            DevItem()
        }
        CardTitle(text = "开发者")
        CardGroup(360.dp) {
            DevItem()
            DevItem()
            DevItem()
            DevItem()
            DevItem()
        }
    }
}

@Composable
fun DevItem(name: String = "名字", text: String = "描述") {
    Row(
        Modifier
            .height(72.dp)
            .fillMaxWidth()
            .clip(QQCleanerShapes.cardGroupBackground)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp),
            painter = painterResource(R.drawable.ic_home_qqcleaner),
            contentDescription = ""
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "测试")
            Text(text = "测试")
        }
        ForwardIcon(id = R.string.item_about)
    }
}