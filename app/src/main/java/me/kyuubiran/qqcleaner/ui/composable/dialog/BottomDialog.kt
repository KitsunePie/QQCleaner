package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes

@Composable
fun BottomDialog(
    onDismissRequest: () -> Unit,
    dialogHeight: Float,
    bottomHeight: Dp = 0.dp,
    state: MutableState<Boolean> = mutableStateOf(true),
    dialogText: String = "Dialog名字",
    content: @Composable () -> Unit,

    ) {
    val background = QQCleanerColorTheme.colors.dialogBackgroundColor
    var flag by remember { mutableStateOf(true) }
    var isDismiss by remember { mutableStateOf(false) }
    val color = remember { Animatable(Color.Transparent) }
    val height = remember { Animatable(0f) }
    val context = LocalContext.current as Activity
    // 通过外部修改 flag 控制 dialog 的关闭与否
    LaunchedEffect(state.value) {
        flag = state.value
    }

    Dialog(
        onRemoveViewRequest = {
            onDismissRequest()
        },
        onDismissRequest = {
            flag = false
        },
        isDismiss = isDismiss
    ) {
        // 判断当前状态来修改颜色和高度
        LaunchedEffect(flag) {
            async {
                // 颜色动画
                color.animateTo(
                    targetValue = if (flag) background else Color.Transparent,
                    animationSpec = tween(600)
                )
            }.onAwait
            async {
                // 高度的动画
                height.animateTo(
                    targetValue = if (flag) dialogHeight + QQCleanerData.navigationBarHeight.value else 0f,
                    animationSpec = tween(600)
                ).apply {
                    if (!flag)
                        isDismiss = true
                }
            }.onAwait
        }
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier.padding(bottom = bottomHeight)
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
                        color = QQCleanerColorTheme.colors.background
                    )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .padding(start = 24.dp, end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = dialogText,
                        modifier = Modifier
                            .weight(1f),
                        style = QQCleanerTypes.DialogTitleStyle,
                        color = QQCleanerColorTheme.colors.textColor
                    )
                    // 图标
                    Box(
                        //为了添加一个比较大的水波纹
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable {
                                flag = false
                                // 这个是收回输入框
                                val imm =
                                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                                imm?.hideSoftInputFromWindow(
                                    context.window.decorView.windowToken,
                                    0
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            tint = QQCleanerColorTheme.colors.textColor,
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = stringResource(
                                id = R.string.dialog_icon_clone
                            )
                        )
                    }
                }
                content.invoke()
            }
        }
    }
}