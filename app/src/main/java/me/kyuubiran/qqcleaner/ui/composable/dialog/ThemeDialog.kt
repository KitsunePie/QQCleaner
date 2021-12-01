package me.kyuubiran.qqcleaner.ui.composable.dialog


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors


@SuppressLint("UnrememberedMutableState")
fun themeDialog(
    context: Context,
    isClickDismiss: Boolean = true,
    isBackToDismiss: Boolean = true,
    isStatusBarLight: Boolean = true,
    isNavigationBarLight: Boolean = true,
    onClick: () -> Unit,
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
                        targetValue = if (flag) 432f else 0f,
                        animationSpec = tween(600)
                    ).apply {
                        if (!flag) removeView()
                    }
                }

            }

            Box(contentAlignment = Alignment.BottomStart) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = color.value)
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
                        Modifier.padding(start = 24.dp, top = 26.dp, end = 24.dp, bottom = 25.dp)
                    ) {
                        Text(text = "主题风格")
                    }
                    Canvas(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .height(1.dp)
                    ) {
                        val canvasQuadrantSize = size
                        drawRect(
                            color = Color.Red,
                            size = canvasQuadrantSize
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 24.dp)
                            .fillMaxWidth()
                            .height(168.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "浅色主题")
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "深色主题")
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "跟随系统")
                        }
                    }
                    Canvas(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .height(1.dp)
                    ) {
                        val canvasQuadrantSize = size
                        drawRect(
                            color = Color.Red,
                            size = canvasQuadrantSize
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "使用纯黑色深色主题")
                    }

                    Row(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth()
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "确定")
                    }
                }
            }
        }

        this.show()
    }
}

