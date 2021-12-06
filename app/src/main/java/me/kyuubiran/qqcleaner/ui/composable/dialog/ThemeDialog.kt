package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import me.kyuubiran.qqcleaner.R.string.confirm
import me.kyuubiran.qqcleaner.R.string.dialog_title_time
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.dialogButtonBackground
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.DialogButtonStyle
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.DialogTitleStyle


@Composable
fun ThemeDialog(
    onDismissRequest: () -> Unit,
) {
    var flag by remember { mutableStateOf(true) }
    var isDismiss by remember { mutableStateOf(false) }
    val color = remember { Animatable(Color.Transparent) }
    val height = remember { Animatable(0f) }


    Dialog(
        onRemoveViewRequest = {
            onDismissRequest()
        },
        onDismissRequest = {
            flag = false
        },
        isDismiss = isDismiss
    ) {
        LaunchedEffect(flag) {
            async {
                color.animateTo(
                    targetValue = if (flag) Color(0x33202124) else Color.Transparent,
                    animationSpec = tween(600)
                )
            }.onAwait
            async {
                height.animateTo(
                    targetValue = if (flag) 432f else 0f,
                    animationSpec = tween(600)
                ).apply {
                    if (!flag)
                        isDismiss = true
                }
            }.onAwait
        }
        Box(
            contentAlignment = Alignment.BottomStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = color.value)
                    .clickable(
                        // 防止击穿
                        onClick = { },
                        // 去掉点击水波纹
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .height((height.value).dp)
                    .background(
                        shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp),
                        color = colors.background
                    )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .padding(start = 24.dp, top = 26.dp, end = 24.dp, bottom = 25.dp)

                ) {
                    Text(
                        text = stringResource(id = dialog_title_time),
                        style = DialogTitleStyle,
                        color = colors.textColor
                    )
                }

                Canvas(
                    modifier = Modifier
                        .padding(top = 4.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                ) {
                    drawRect(
                        color = Color(0xFFF7F7F7),
                        size = this.size
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(
                            color = colors.dialogButtonDefault,
                            shape = dialogButtonBackground
                        )
                        .padding(16.dp)
                ) {
                    Text(text = "亮色主题", color = colors.dialogButtonTextDefault)
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(shape = dialogButtonBackground, color = Color.Transparent)
                        .padding(16.dp)
                ) {
                    Text(text = "暗色主题")
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(shape = dialogButtonBackground, color = Color.Transparent)
                        .padding(16.dp)
                ) {
                    Text(text = "跟随系统")
                }

                Canvas(
                    modifier = Modifier
                        .padding(top = 12.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                ) {
                    drawRect(
                        color = Color(0xFFF7F7F7),
                        size = this.size
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(shape = dialogButtonBackground, color = Color.Transparent)
                        .padding(16.dp)
                ) {
                    Text(text = "使用纯黑色深色主题")
                }

                val dialogButtonColor by animateColorAsState(
                    if (true)
                        colors.dialogButtonDefault else colors.dialogButtonPress,
                    tween(600)
                )

                val dialogButtonTextColor by animateColorAsState(
                    if (true)
                        colors.dialogButtonTextDefault else colors.dialogButtonTextPress,
                    tween(600)
                )
                Row(
                    modifier = Modifier
                        .padding(start = 24.dp, top = 24.dp, end = 24.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            color = dialogButtonColor,
                            shape = dialogButtonBackground
                        )
                        .clip(shape = dialogButtonBackground)
                        .clickable(enabled = false, onClick = {}),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier,
                        style = DialogButtonStyle,
                        color = dialogButtonTextColor,
                        textAlign = TextAlign.Center,
                        text = stringResource(id = confirm)
                    )
                }

            }
        }
    }
}