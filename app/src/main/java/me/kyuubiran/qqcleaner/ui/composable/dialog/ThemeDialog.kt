package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.content.res.Resources
import android.graphics.Rect
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors


@Composable
fun ThemeDialog() {
    val context = LocalContext.current as Activity
    var flag by remember { mutableStateOf(false) }
    val color = remember { Animatable(Color.Transparent) }
    val height = remember { Animatable(0f) }
    var isDismiss by remember { mutableStateOf(true) }
    var softKeyboardHeight by remember { mutableStateOf(0f) }
    var windowHeight = 0
    context.window.decorView.apply {
        this.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            this.getWindowVisibleDisplayFrame(r)
            val visibleHeight = r.height()
            if (windowHeight == 0)
                windowHeight = visibleHeight
            else
                softKeyboardHeight =
                    if (windowHeight == visibleHeight) 0f
                    else (windowHeight - visibleHeight) / Resources.getSystem().displayMetrics.density
        }
    }
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

            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier
                    .padding(bottom = softKeyboardHeight.dp)
                    .clickable(
                        // 防止击穿
                        onClick = {},
                        // 去掉点击水波纹
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            ) {
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
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        value = "测试",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions.Default,
                        cursorBrush = SolidColor(colors.themeColor),
                        onValueChange = {
                            "测试"
                        }
                    )
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
