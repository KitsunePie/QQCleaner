package me.kyuubiran.qqcleaner.ui.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import me.kyuubiran.qqcleaner.ui.utils.fillMaxModifier
import me.kyuubiran.qqcleaner.ui.utils.fillMaxWidthModifier


/**
 * 这个是一个消息弹窗
 * @param context 对应 context 为所需参数
 * @param titleText 标题
 * @param tipText 提示文本
 * @param isClickDismiss 是否点击背景返回
 * @param isBackToDismiss 是否返回键返回
 * @param isNavigationBarLight 导航栏是否为暗色
 * @param isStatusBarLight 状态栏是否为暗色
 */
@SuppressLint("UnrememberedMutableState")
fun messageDialog(
    context: Context,
    titleText: String = "测试",
    tipText: String = "内容测试啦啦啦啦",
    isClickDismiss: Boolean = true,
    isBackToDismiss: Boolean = true,
    isStatusBarLight: Boolean = true,
    isNavigationBarLight: Boolean = true,
    onClick: () -> Unit
) {
    BaseDialog(context = context).apply {
        clickToDismiss(isClickDismiss)
        backToDismiss(isBackToDismiss)
        setStatusBarLightMode(isStatusBarLight)
        setNavigationBarLightMode(isNavigationBarLight)
        this.setContent {
            var flag by remember { mutableStateOf(true) }
            val color = remember { Animatable(Color.Transparent) }
            val height = remember { Animatable(0f) }
            dismissBlock {
                flag = false
            }
            LaunchedEffect(flag) {
                async {
                    color.animateTo(
                        targetValue = if (flag) Color(0x33202124) else Color.Transparent,
                        animationSpec = tween(600)
                    )
                }

                async {
                    height.animateTo(
                        targetValue = if (flag) 196f else 0f,
                        animationSpec = tween(600)
                    ).apply {
                        if (!flag) removeView()
                    }
                }

            }

            Box(contentAlignment = Alignment.BottomStart) {
                Box(modifier = fillMaxModifier.background(color = color.value))
                Column(
                    modifier = fillMaxWidthModifier
                        .align(Alignment.BottomStart)
                        .height((height.value).dp)
                        .background(
                            shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
                            color = Color(0xFFFFFFFF)
                        )
                ) {
                    Text(text = titleText, Modifier.clickable {
                        onClick.invoke()
                    })
                    Text(text = tipText)
                }
            }
        }

        this.show()
    }
}

