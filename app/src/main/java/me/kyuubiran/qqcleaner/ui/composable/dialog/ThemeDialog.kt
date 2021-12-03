package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors

@Composable
fun ThemeDialog() {
    var flag by remember { mutableStateOf(false) }
    val color = remember { Animatable(Color.Transparent) }
    val height = remember { Animatable(0f) }
    var isDismiss by remember { mutableStateOf(true) }
    if (isDismiss) {
        flag = true
        Dialog(onDismissRequest = { flag = false }) {
            LaunchedEffect(flag) {
                async {
                    color.animateTo(
                        targetValue = if (flag) Color(0x33202124) else Color.Transparent,
                        animationSpec = tween(600)
                    )
                }
                async {
                    height.animateTo(
                        targetValue = if (flag) 240f else 0f,
                        animationSpec = tween(600)
                    ).apply {
                        if (!flag) {
                            isDismiss = false
                        }
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
                        Text(text = "设置自动瘦身间隔")
                    }
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp), value = "测试", onValueChange = {
                            "测试"
                        })
                    Row(
                        Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp)
                    ) {
                        Text(text = "确定")
                    }
                }
            }
        }
    }
}