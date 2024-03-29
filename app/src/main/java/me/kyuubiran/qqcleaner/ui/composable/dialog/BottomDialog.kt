package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes
import me.kyuubiran.qqcleaner.ui.util.hideKeyBoard
import me.kyuubiran.qqcleaner.ui.util.noClick

@Composable
fun BottomDialog(
    onDismissRequest: () -> Unit,
    dialogHeight: Float,
    bottomHeight: Dp = 0.dp,
    state: MutableState<Boolean> = mutableStateOf(true),
    dialogText: String = "Dialog名字",
    content: @Composable ColumnScope.() -> Unit,
) {
    val background = colors.maskColor
    var flag by remember { mutableStateOf(true) }
    var isDismiss by remember { mutableStateOf(false) }
    val color = remember { Animatable(Color.Transparent) }
    val height = remember { Animatable(0f) }
    val context = LocalContext.current as Activity
    val insets = LocalWindowInsets.current

    val navigationHeight = with(LocalDensity.current) { insets.navigationBars.bottom.toDp() }
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
            listOf(
                // 为了并行而这么写的，具体能怎么改我不清楚
                async {
                    // 颜色动画
                    color.animateTo(
                        targetValue = if (flag) background else Color.Transparent,
                        animationSpec = tween(600)
                    )
                },
                async {
                    // 高度的动画
                    height.animateTo(
                        targetValue = if (flag) dialogHeight + navigationHeight.value else 0f,
                        animationSpec = tween(600)
                    )
                }
            ).awaitAll()
            if (!flag) isDismiss = true
        }
        LaunchedEffect(dialogHeight) {
            if (flag) {
                height.animateTo(
                    targetValue = dialogHeight + navigationHeight.value,
                    animationSpec = tween(600)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomHeight)
                .background(color = color.value)
                .noClick()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(BottomStart)
                    .height((height.value).dp)
                    .background(
                        shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp),
                        color = colors.dialogBackgroundColor
                    )
                    .padding(bottom = navigationHeight)
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
                        color = colors.firstTextColor
                    )
                    // 图标
                    Box(
                        // 添加一个比较大的水波纹
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable {
                                flag = false
                                // 这个是收回输入框
                                context.hideKeyBoard()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            tint = colors.firstTextColor,
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = stringResource(
                                id = R.string.dialog_icon_clone
                            )
                        )
                    }
                }
                content()
            }
        }
    }
}