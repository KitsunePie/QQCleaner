package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes

@Composable
fun EditText(
    modifier: Modifier,
    text: MutableState<String>,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    onKeyEvent: KeyEvent.() -> Boolean,
    hintText: String
) {

    Box(
        // 这是居中，但是没什么必要
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = colors.typeBoxBackgroundColor,
                shape = QQCleanerShapes.dialogEditBackGround
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
                    it.onKeyEvent()
                },
            value = text.value,
            keyboardOptions = keyboardOptions,
            onValueChange = onValueChange
        )
        val editHintColor by animateColorAsState(
            if (text.value.isEmpty())
                colors.disableSecondTextColor else Color.Transparent,
            tween(100)
        )

        Text(
            text = hintText,
            color = editHintColor,
            style = QQCleanerTypes.DialogEditStyle,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        )
    }
}