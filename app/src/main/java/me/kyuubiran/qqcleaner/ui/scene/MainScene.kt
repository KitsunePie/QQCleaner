package me.kyuubiran.qqcleaner.ui.scene


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.QQCleanerViewModel
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.composable.Switch
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
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.itemTextStyle
import me.kyuubiran.qqcleaner.ui.utils.drawColoredShadow
import me.kyuubiran.qqcleaner.util.ConfigManager
import me.kyuubiran.qqcleaner.util.getCurrentTimeText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScene(viewModel: QQCleanerViewModel = viewModel(), navController: NavController) {
    var isTime by remember { mutableStateOf(false) }
    if (isTime) {
        TimeDialog {
            isTime = false
        }
    }
    var isTheme by remember { mutableStateOf(false) }
    if (isTheme) {
        ThemeDialog {
            isTheme = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column {
            //标题
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = viewModel.statusBarHeight)
                    .height(148.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Text(
                        text = getCurrentTimeText(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        style = TitleTextStyle,
                        color = colors.textColor
                    )
                    Text(
                        text = stringResource(R.string.last_cleaner, 5),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        style = SubTitleTextStyle,
                        color = colors.textColor
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .height(18.dp)
                            .background(
                                colors.themeColor,
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "11 / 21 03:00",
                            color = colors.buttonTextColor,
                            style = ButtonTitleTextStyle
                        )
                    }
                }
                Image(
                    modifier = Modifier
                        .size(88.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.ic_icon),
                    contentDescription = "图标",

                    )
            }
            Box(
                modifier = Modifier
                    .drawColoredShadow(
                        colors.cardBackgroundShadowColor,
                        0.1f,
                        shadowRadius = 10.dp,
                        offsetX = 0.dp,
                        offsetY = (-3).dp
                    )
                    .background(
                        colors.cardBackgroundColor,
                        RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )
            ) {
                //设定
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(shape = cardBackground, color = colors.cardBackgroundColor)
                ) {
                    CardTitle(text = stringResource(id = R.string.title_setup))

                    CardGroup(168.dp) {
                        //自动瘦身
                        Item(
                            text = stringResource(id = R.string.item_cleaner),
                            onClick = {
                            }
                        ) {

                            Switch()
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_toggle_on),
                                contentDescription = stringResource(id = R.string.item_cleaner),
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(36.dp),
                                tint = colors.iconColor
                            )
                        }

                        Item(
                            text = stringResource(id = R.string.silence_clean),
                            onClick = {
                                if (!ConfigManager.sSilenceClean) Log.toast(appContext.getString(R.string.silence_clean_toast))
                                ConfigManager.sSilenceClean = !ConfigManager.sSilenceClean
                            }
                        ) {

                            Switch()
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_toggle_on),
                                contentDescription = stringResource(id = R.string.item_cleaner),
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(36.dp),
                                tint = colors.iconColor
                            )
                        }

                        Item(
                            stringResource(id = R.string.item_cleaner_time),
                            onClick = { isTime = true }
                        ) {
                            Text(
                                text = stringResource(id = R.string.item_cleaner_time_tip, 24),
                                color = colors.textColor,
                                style = TipStyle
                            )
                        }
                        Item(
                            text = stringResource(id = R.string.item_cleaner_config),
                            onClick = {
                                navController.navigate(QQCleanerApp.Edit) {
                                    popUpTo(QQCleanerApp.Main)
                                }
                            }
                        ) {
                            ForwardIcon(id = R.string.item_cleaner_config)
                        }
                    }


                    //更多
                    CardTitle(text = stringResource(id = R.string.title_more))

                    CardGroup(112.dp) {
                        //切换主题
                        Item(
                            text = stringResource(id = R.string.item_theme),
                            onClick = {
                                isTheme = true
                            }
                        ) {
                            ForwardIcon(id = R.string.item_theme)
                        }
                        //关于
                        Item(
                            text = stringResource(id = R.string.item_about),
                            onClick = {
                                navController.navigate(QQCleanerApp.Developer)
                            }
                        ) {
                            ForwardIcon(R.string.item_about)
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun ForwardIcon(@StringRes id: Int) {
    Icon(
        painter = painterResource(id = R.drawable.ic_baseline_arrow_forward),
        contentDescription = stringResource(id = id),
        modifier = Modifier.size(24.dp),
        tint = colors.iconColor
    )
}

@Composable
private fun CardGroup(height: Dp, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(horizontal = 24.dp)
            .background(shape = cardGroupBackground, color = colors.background),
        content = content
    )
}

@Composable
private fun CardTitle(text: String) {
    Text(
        text = text,
        style = cardTitleTextStyle,
        color = colors.textColor,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    )
}

@Composable
private fun Item(text: String, onClick: () -> Unit = {}, content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(cardGroupBackground)
            .clickable { onClick.invoke() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = itemTextStyle,
            color = colors.textColor,
            modifier = Modifier.weight(1f),
        )
        content()
    }
}

