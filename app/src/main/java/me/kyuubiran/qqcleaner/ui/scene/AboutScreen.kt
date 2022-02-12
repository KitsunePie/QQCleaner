package me.kyuubiran.qqcleaner.ui.scene

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.composable.Item
import me.kyuubiran.qqcleaner.ui.composable.TopBar
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.AboutTextStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.VersionTextStyle

@Composable
fun AboutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.pageBackgroundColor)
            .padding(top = QQCleanerData.statusBarHeight)
    ) {
        TopBar(
            click = {
                navController.popBackStack(navController.graph.startDestinationId, false)
            },
            stringResource(id = R.string.title_about)
        )
        Crossfade(
            targetState = QQCleanerData.isDark,
            animationSpec = tween(600),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(96.dp),
                    painter = painterResource(
                        id =
                        if (it)
                            R.drawable.ic_home_qqcleaner_dark
                        else
                            R.drawable.ic_home_qqcleaner
                    ),
                    contentDescription = stringResource(id = R.string.icon_content_description),
                )

                Text(
                    text = "瘦身模块",
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .align(Alignment.CenterHorizontally),
                    style = AboutTextStyle,
                    color = colors.firstTextColor
                )
                Text(
                    text = "版本",
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .align(Alignment.CenterHorizontally),
                    style = VersionTextStyle,
                    color = colors.secondTextColor
                )
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                )
                CardGroup(56.dp) {
                    Item(
                        text = "Github",
                        onClick = {
                            navController.navigate(QQCleanerApp.Developer)
                        },
                        content = {
                            ForwardIcon(R.string.item_about)
                        }
                    )
                }

                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                )
                CardGroup(112.dp) {
                    Item(
                        text = "Telegram 频道",
                        onClick = {
                            navController.navigate(QQCleanerApp.Developer)
                        },
                        content = {
                            ForwardIcon(R.string.item_about)
                        }
                    )
                    Item(
                        text = "QQ 群组",
                        onClick = {
                            navController.navigate(QQCleanerApp.Developer)
                        },
                        content = {
                            ForwardIcon(R.string.item_about)
                        }
                    )
                }

                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                )
                CardGroup(56.dp) {
                    Item(
                        text = stringResource(id = R.string.item_about),
                        onClick = {
                            navController.navigate(QQCleanerApp.Developer)
                        },
                        content = {
                            ForwardIcon(R.string.item_about)
                        }
                    )
                }
            }

        }
    }
}