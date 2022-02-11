package me.kyuubiran.qqcleaner.ui.scene

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.QQCleanerData.isDark
import me.kyuubiran.qqcleaner.QQCleanerData.statusBarHeight
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.composable.Fab
import me.kyuubiran.qqcleaner.ui.composable.Item
import me.kyuubiran.qqcleaner.ui.composable.SwitchItem
import me.kyuubiran.qqcleaner.ui.composable.dialog.ThemeDialog
import me.kyuubiran.qqcleaner.ui.composable.dialog.TimeDialog
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardGroupBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.ButtonTitleTextStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.SubTitleTextStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.TipStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.TitleTextStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.cardTitleTextStyle
import me.kyuubiran.qqcleaner.ui.util.drawColoredShadow
import me.kyuubiran.qqcleaner.util.*

@Composable
fun MainScreen(navController: NavController) {

    // 上次瘦身日期
    var lastClean by remember {
        mutableStateOf(ConfigManager.sLastCleanDate)
    }
    // 自动瘦身
    val autoClean by remember {
        mutableStateOf(ConfigManager.sAutoClean)
    }
    // 自动瘦身间隔
    var autoCleanInterval by remember {
        mutableStateOf(ConfigManager.sAutoCleanInterval)
    }
    // 静默瘦身
    val silenceClean by remember {
        mutableStateOf(ConfigManager.sSilenceClean)
    }

    // 设置间隔Dialog
    var timeDialogShow by remember { mutableStateOf(false) }
    if (timeDialogShow) {
        TimeDialog { text ->
            timeDialogShow = false
            autoCleanInterval = (text.toIntOrNull() ?: 24).also {
                ConfigManager.sAutoCleanInterval = it
            }
        }
    }
    // 设置主题Dialog
    var themeDialogShow by remember { mutableStateOf(false) }
    if (themeDialogShow) {
        ThemeDialog { themeDialogShow = false }
    }

    // 主UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.appBarsAndItemBackgroundColor)
    ) {
        Column {
            // 标题
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = statusBarHeight)
                    .height(148.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    // 当前时间段
                    Text(
                        text = getCurrentTimeText(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .height(29.dp),
                        style = TitleTextStyle,
                        color = colors.secondTextColor
                    )
                    // 上次瘦身日期
                    Text(
                        text = if (lastClean > 0L) stringResource(
                            R.string.last_clean_date,
                            getFormatCleanTimeText(lastClean)
                        ) else stringResource(id = R.string.no_last_clean_date_record),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(21.dp),
                        style = SubTitleTextStyle,
                        color = colors.secondTextColor
                    )
                    // 蓝色日期
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 24.dp)
                            .height(18.dp)
                            .background(
                                color = colors.mainThemeColor,
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (lastClean > 0L) stringResource(
                                id = R.string.last_clean_date_title,
                                getLastCleanTimeText(lastClean)
                            ) else stringResource(id = R.string.no_last_clean_date_record_mini),
                            color = colors.whiteColor,
                            style = ButtonTitleTextStyle
                        )
                    }
                }
                Crossfade(targetState = isDark, animationSpec = tween(600)) {
                    Image(
                        modifier = Modifier
                            .padding(vertical = 30.dp)
                            .size(88.dp),
                        painter = painterResource(
                            id =
                            if (it)
                                R.drawable.ic_home_qqcleaner_dark
                            else
                                R.drawable.ic_home_qqcleaner
                        ),
                        contentDescription = stringResource(id = R.string.icon_content_description),
                    )
                }

            }
            Box(
                modifier = Modifier
                    .drawColoredShadow(
                        colors.rippleColor,
                        0.1f,
                        shadowRadius = 10.dp,
                        offsetX = 0.dp,
                        offsetY = (-3).dp
                    )
                    .background(
                        colors.pageBackgroundColor,
                        RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )
            ) {
                //设定
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(shape = cardBackground, color = colors.pageBackgroundColor)
                ) {
                    CardTitle(text = stringResource(id = R.string.title_setup))

                    CardGroup(224.dp) {
                        //自动瘦身
                        SwitchItem(
                            text = stringResource(id = R.string.item_cleaner),
                            checked = rememberMutableStateOf(autoClean),
                            onClick = {
                                ConfigManager.sAutoClean = it
                            }
                        )

                        //静默瘦身
                        SwitchItem(
                            text = stringResource(id = R.string.silence_clean),
                            checked = rememberMutableStateOf(
                                silenceClean
                            ), onClick = {
                                Log.toast(moduleRes.getString(if (it) R.string.silence_clean_toast_on else R.string.silence_clean_toast_off))
                                ConfigManager.sSilenceClean = it
                            }
                        )

                        Item(
                            stringResource(id = R.string.item_cleaner_time),
                            onClick = { timeDialogShow = true },
                            content = {
                                Text(
                                    text = stringResource(
                                        id = R.string.item_cleaner_time_tip,
                                        autoCleanInterval
                                    ),
                                    color = colors.itemRightTextColor,
                                    style = TipStyle
                                )
                            }
                        )
                        Item(
                            text = stringResource(id = R.string.item_cleaner_config),
                            onClick = {
                                navController.navigate(QQCleanerApp.Config) {
                                    popUpTo(QQCleanerApp.Main)
                                }
                            },
                            content = {
                                ForwardIcon(id = R.string.item_cleaner_config)
                            }
                        )
                    }

                    //更多
                    CardTitle(text = stringResource(id = R.string.title_more))

                    CardGroup(112.dp) {
                        //切换主题
                        Item(
                            text = stringResource(id = R.string.item_theme),
                            onClick = {
                                themeDialogShow = true
                            },
                            content = {
                                ForwardIcon(id = R.string.item_theme)
                            })
                        //关于
                        Item(
                            text = stringResource(id = R.string.item_about),
                            onClick = {
                                navController.navigate(QQCleanerApp.About)
                            },
                            content = {
                                ForwardIcon(R.string.item_about)
                            }
                        )
                    }

                }

                // 立即瘦身
                Fab(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    text = stringResource(id = R.string.cleaner_text),
                    onClick = {
                        CleanManager.executeAll()
                        System.currentTimeMillis().let {
                            lastClean = it
                            ConfigManager.sLastCleanDate = it
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun ForwardIcon(@StringRes id: Int) {
    Icon(
        painter = painterResource(id = R.drawable.ic_chevron_right),
        contentDescription = stringResource(id = id),
        modifier = Modifier.size(24.dp),
        tint = colors.itemRightIconColor
    )
}

@Composable
private fun CardGroup(height: Dp, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(horizontal = 24.dp)
            .background(shape = cardGroupBackground, color = colors.appBarsAndItemBackgroundColor),
        content = content
    )
}

@Composable
private fun CardTitle(text: String) {
    Text(
        text = text,
        style = cardTitleTextStyle,
        color = colors.secondTextColor,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    )
}

