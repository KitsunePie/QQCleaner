package me.kyuubiran.qqcleaner.ui.scene

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.SubTitleText
import me.kyuubiran.qqcleaner.ui.TitleText
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.buttonType
import me.kyuubiran.qqcleaner.ui.theme.tipTileType
import me.kyuubiran.qqcleaner.ui.utils.drawColoredShadow
import me.kyuubiran.qqcleaner.ui.utils.fillMaxModifier
import me.kyuubiran.qqcleaner.ui.utils.fillMaxWidthModifier
import me.kyuubiran.qqcleaner.ui.view.*
import me.kyuubiran.qqcleaner.ui.view.dialog.messageDialog
import me.kyuubiran.qqcleaner.util.getCurrentTimeText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScene(navController: NavController) {
    val content = LocalContext.current
    Column(
        modifier = fillMaxModifier
            .background(color = QQCleanerColorTheme.colors.background)
            .padding(top = 24.dp)
    ) {
        Row(
            modifier = fillMaxWidthModifier
                .height(172.dp)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                TitleText(text = getCurrentTimeText())

                SubTitleText(
                    text = "上次瘦身是 5 天前",
                    modifier = Modifier
                        .padding(top = 16.dp),
                )
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(18.dp)
                        .width(102.dp)
                        .background(
                            QQCleanerColorTheme.colors.themeColor,
                            RoundedCornerShape(4.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "上次瘦身 11 / 21 03:00",
                        color = QQCleanerColorTheme.colors.buttonTextColor,
                        style = tipTileType
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.ic_icon),
                contentDescription = "图标",
                modifier = Modifier.size(88.dp)
            )
        }

        Box(
            modifier = Modifier
                .drawColoredShadow(
                    Color(0xFF3C4043),
                    0.1f,
                    shadowRadius = 10.dp,
                    offsetX = 0.dp,
                    offsetY = (-3).dp
                )
                .background(
                    QQCleanerColorTheme.colors.cardBackgroundColor,
                    RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                )
        ) {
            Column(
                modifier = fillMaxModifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleCard("设定", 168.dp) {
                    ItemSwitchCard("自动瘦身", "自动瘦身")
                    ItemTextCard("自动瘦身间隔", "24 h", onClick = {
                        messageDialog(content)
                    })
                    ItemMenuCard("瘦身配置", "瘦身配置", onClick = {
                        navController.navigate("edit_screen")
                    })
                }
                TitleCard("更多", 112.dp) {
                    ItemMoreCard("主题风格", "主题风格", onClick = {

                    })
                    ItemMenuCard("关于瘦身", "关于瘦身", onClick = {
                        navController.navigate("developer_screen")
                    })
                }

                Column(
                    modifier = fillMaxModifier.paddingFromBaseline(bottom = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .height(35.dp)
                            .width(96.dp)
                            .drawColoredShadow(
                                QQCleanerColorTheme.colors.themeElevationColor,
                                0.1f,
                                shadowRadius = 6.dp,
                                offsetX = 0.dp,
                                offsetY = 3.dp
                            )
                            .background(
                                QQCleanerColorTheme.colors.themeColor,
                                RoundedCornerShape(80.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "立即瘦身",
                            color = QQCleanerColorTheme.colors.buttonTextColor,
                            style = buttonType
                        )

                    }
                }
            }
        }
    }
}