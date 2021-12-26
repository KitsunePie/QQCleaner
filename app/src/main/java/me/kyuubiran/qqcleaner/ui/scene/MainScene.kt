package me.kyuubiran.qqcleaner.ui.scene

import androidx.annotation.StringRes
import androidx.compose.foundation.*
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
import me.kyuubiran.qqcleaner.QQCleanerViewModel
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.composable.Switch
import me.kyuubiran.qqcleaner.ui.composable.dialog.ConfigDialog
import me.kyuubiran.qqcleaner.ui.composable.dialog.ThemeDialog
import me.kyuubiran.qqcleaner.ui.composable.dialog.TimeDialog
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.cardGroupBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.ButtonTitleTextStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.SubTitleTextStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.TipStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.TitleTextStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.cardTitleTextStyle
import me.kyuubiran.qqcleaner.ui.utils.drawColoredShadow
import me.kyuubiran.qqcleaner.util.getCurrentTimeText
import me.kyuubiran.qqcleaner.util.getFormatCleanTimeText
import me.kyuubiran.qqcleaner.util.getLastCleanTimeText
import me.kyuubiran.qqcleaner.util.rememberMutableStateOf


@Composable
fun MainScene(viewModel: QQCleanerViewModel = viewModel(), navController: NavController) {

    // 上次瘦身日期
    var lastClean by remember {
        mutableStateOf(System.currentTimeMillis())
//        mutableStateOf(ConfigManager.sLastCleanDate)
    }
    // 自动瘦身间隔
    var autoCleanInterval by remember {
        mutableStateOf(24)
//        mutableStateOf(ConfigManager.sAutoCleanInterval)
    }

    // 设置间隔Dialog
    var timeDialogShow by remember { mutableStateOf(false) }
    if (timeDialogShow) {
        TimeDialog { timeDialogShow = false }
    }
    // 设置编辑文本
    var isEdit by remember { mutableStateOf(false) }
    if (isEdit) {
        ConfigDialog {
            isEdit = false
        }
    }
    // 设置主题Dialog
    var themeDialogShow by remember { mutableStateOf(false) }
    if (themeDialogShow) {
        ThemeDialog(viewModel) { themeDialogShow = false }
    }

    // 主UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column {
            // 标题
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
                    // 当前时间段
                    Text(
                        text = getCurrentTimeText(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .height(29.dp),
                        style = TitleTextStyle,
                        color = colors.textColor
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
                        color = colors.textColor
                    )
                    // 蓝色日期
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 24.dp)
                            .height(18.dp)
                            .background(
                                colors.themeColor,
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (lastClean > 0L) stringResource(
                                id = R.string.last_clean_date_title,
                                getLastCleanTimeText(lastClean)
                            ) else stringResource(id = R.string.no_last_clean_date_record_mini),
                            color = colors.buttonTextColor,
                            style = ButtonTitleTextStyle
                        )
                    }
                }
                Image(
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                        .size(88.dp),
                    painter = painterResource(id = R.drawable.ic_icon),
                    contentDescription = stringResource(id = R.string.icon_content_description),
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

                    CardGroup(224.dp) {
                        //自动瘦身
                        SwitchItem(
                            text = stringResource(id = R.string.item_cleaner),
                            checked = rememberMutableStateOf(
                                value = true
                                //value = ConfigManager.sAutoClean
                            ),
                            onClick = {
                                //ConfigManager.sAutoClean = it
                            }
                        )

                        //静默瘦身
                        SwitchItem(
                            text = stringResource(id = R.string.silence_clean),
                            checked = rememberMutableStateOf(
                                value = true
                                // value = ConfigManager.sSilenceClean
                            ), onClick = {
//                                if (it) Log.toast(appContext.getString(R.string.silence_clean_toast))
//                                ConfigManager.sSilenceClean = it
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
                                    color = colors.textColor,
                                    style = TipStyle
                                )
                            }
                        )
                        Item(
                            text = stringResource(id = R.string.item_cleaner_config),
                            onClick = {

                                navController.navigate(QQCleanerApp.Edit) {
                                    popUpTo(QQCleanerApp.Main)
                                }
                            },
                            onLongClick = {
                                isEdit = true
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
fun Item(text: String, onClick: () -> Unit = {}, content: @Composable () -> Unit) {
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
            style = QQCleanerTypes.itemTextStyle,
            color = colors.textColor,
            modifier = Modifier.weight(1f),
        )
        content()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Item(
    text: String,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(cardGroupBackground)
            .combinedClickable(
                onLongClick = { onLongClick() },
                onClick = { onClick() })
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = QQCleanerTypes.itemTextStyle,
            color = colors.textColor,
            modifier = Modifier.weight(1f),
        )
        content()
    }
}

@Composable
private fun SwitchItem(
    text: String,
    checked: MutableState<Boolean>,
    onClick: ((Boolean) -> Unit)? = null,
    clickNoToggle: Boolean = false
) {
    fun toggle() {
        if (onClick == null) {
            checked.value = !checked.value
        } else {
            if (!clickNoToggle) checked.value = !checked.value
            onClick(checked.value)
        }
    }

    Item(text = text, onClick = { toggle() }) {
        Switch(checked = checked)
    }
}
