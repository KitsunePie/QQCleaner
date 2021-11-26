package me.kyuubiran.qqcleaner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.buttonType
import me.kyuubiran.qqcleaner.ui.utils.shadow
import me.kyuubiran.qqcleaner.ui.view.ItemCard
import me.kyuubiran.qqcleaner.ui.view.TitleCard
import me.kyuubiran.qqcleaner.util.fillMaxModifier
import me.kyuubiran.qqcleaner.util.fillMaxWidthModifier


/**
 * QQCleaner App 的 UI 唯一入口点
 *
 * @author Agoines
 */
@Composable
fun QQCleanerApp() {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = QQCleanerColorTheme.colors.background)
    ) {
        Column(
            modifier = fillMaxWidthModifier
                .height(172.dp)
                .padding(top = 48.dp, start = 24.dp)
        ) {
            TitleText(text = "晚上好，")

            SubTitleText(
                text = "上次瘦身是 5 天前",
                modifier = Modifier
                    .padding(top = 16.dp),
            )
        }
        Box(
            modifier = Modifier
                .shadow(
                    Shadow(
                        Color(0x14202124),
                        Offset(0f, -3f),
                        3f
                    ),
                    RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
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
                    ItemCard("自动瘦身", R.drawable.ic_baseline_arrow_forward_ios_12, "自动瘦身")
                    ItemCard("自动瘦身间隔", R.drawable.ic_baseline_arrow_forward_ios_12, "自动瘦身")
                    ItemCard("瘦身配置", R.drawable.ic_baseline_arrow_forward_ios_12, "自动瘦身")
                }
                TitleCard("更多", 112.dp) {
                    ItemCard("主题风格", R.drawable.ic_baseline_arrow_forward_ios_12, "自动瘦身")
                    ItemCard("关于瘦身", R.drawable.ic_baseline_arrow_forward_ios_12, "自动瘦身")
                }
                Box(
                    modifier = Modifier
                        .shadow(
                            Shadow(
                                QQCleanerColorTheme.colors.themeElevationColor,
                                Offset(0f, 3f),
                                6f
                            ),
                            RoundedCornerShape(80.dp)
                        )
                        .height(35.dp)
                        .width(96.dp)
                        .background(
                            QQCleanerColorTheme.colors.themeColor,
                            RoundedCornerShape(80.dp)
                        )
                        .paddingFromBaseline(bottom = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "立即瘦身",
                        color = QQCleanerColorTheme.colors.titleTextColor,
                        style = buttonType
                    )

                }

            }
        }
    }


}