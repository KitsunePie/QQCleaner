package me.kyuubiran.qqcleaner.ui.composable.dialog

import android.app.Activity
import android.content.res.Resources.getSystem
import android.graphics.Rect
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.R
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK
import android.view.WindowInsets
import android.view.WindowInsets.Type.ime
import android.view.WindowInsetsAnimation
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import me.kyuubiran.qqcleaner.R.string.dialog_title_time
import me.kyuubiran.qqcleaner.R.string.set_auto_clean_interval_desc
import me.kyuubiran.qqcleaner.ui.composable.TextField
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes.dialogEditBackGround
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes.DialogTitleStyle


@Composable
fun TimeDialog(
    onDismissRequest: () -> Unit,
) {

    val context = LocalContext.current as Activity
    var flag by remember { mutableStateOf(true) }
    var isDismiss by remember { mutableStateOf(false) }
    val color = remember { Animatable(Color.Transparent) }
    val height = remember { Animatable(0f) }
    var softKeyboardHeight by remember { mutableStateOf(0f) }
    val bottomHeight = remember { Animatable(0f) }
    var hasKeyboard by remember { mutableStateOf(false) }
    var windowHeight = 0

    var text by remember { mutableStateOf("") }
    context.window.decorView.apply {
        if (SDK_INT >= R) {
            val callback =
                object : WindowInsetsAnimation.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
                    override fun onProgress(
                        insets: WindowInsets,
                        animations: MutableList<WindowInsetsAnimation>
                    ): WindowInsets {
                        softKeyboardHeight =
                            (insets.getInsets(ime()).bottom / getSystem().displayMetrics.density)
                        return insets
                    }
                }
            this.setWindowInsetsAnimationCallback(callback)
        } else {
            this.viewTreeObserver.addOnGlobalLayoutListener {
                val r = Rect()
                this.getWindowVisibleDisplayFrame(r)
                val visibleHeight = r.height()
                if (windowHeight == 0) {
                    windowHeight = visibleHeight
                } else {
                    softKeyboardHeight =
                        if (windowHeight == visibleHeight) {
                            hasKeyboard = false
                            0f
                        } else {
                            hasKeyboard = true
                            (windowHeight - visibleHeight) / getSystem().displayMetrics.density
                        }
                }
            }
        }
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
        LaunchedEffect(flag) {
            async {
                color.animateTo(
                    targetValue = if (flag) Color(0x33202124) else Color.Transparent,
                    animationSpec = tween(600)
                )
            }.onAwait
            async {
                height.animateTo(
                    targetValue = if (flag) 260f else 0f,
                    animationSpec = tween(600)
                ).apply {
                    if (!flag)
                        isDismiss = true
                }
            }.onAwait
        }
        if (SDK_INT < R) LaunchedEffect(hasKeyboard) {
            bottomHeight.animateTo(
                targetValue = if (hasKeyboard) softKeyboardHeight else 0f,
                animationSpec = spring(1.6f)
            )
        }
        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier.padding(
                bottom = if (SDK_INT >= R)
                    softKeyboardHeight.dp
                else bottomHeight.value.dp
            )
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
                    Modifier.padding(start = 24.dp, top = 26.dp, end = 24.dp, bottom = 25.dp)
                ) {
                    Text(
                        text = stringResource(id = dialog_title_time),
                        style = DialogTitleStyle,
                        color = colors.textColor
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(56.dp)
                        .background(
                            color = colors.dialogEditColor,
                            shape = dialogEditBackGround
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 16.dp
                        ),
                ) {
                    TextField(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .onKeyEvent {
                                if (it.nativeKeyEvent.action == ACTION_UP
                                    && it.nativeKeyEvent.keyCode == KEYCODE_BACK
                                ) {
                                    if (softKeyboardHeight == 0f)
                                        flag = false
                                    return@onKeyEvent true
                                }
                                false
                            },
                        value = text,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { value ->
                            text = value.filter {
                                it.isDigit()
                            }
                        }
                    )
                    val editHintColor by animateColorAsState(
                        if (text.isEmpty())
                            colors.dialogEditHintColor else colors.transparent,
                        tween(100)
                    )

                    Text(
                        text = stringResource(id = set_auto_clean_interval_desc),
                        color = editHintColor,
                        style = DialogTitleStyle,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                    )
                }
                DialogButton(text.isNotEmpty(), {})

            }
        }
    }
}
