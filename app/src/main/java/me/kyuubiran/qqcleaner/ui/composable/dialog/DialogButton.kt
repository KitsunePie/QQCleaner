package me.kyuubiran.qqcleaner.ui.composable.dialog

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes

@Composable
fun DialogButton(
    isFix: Boolean,
    onClick: () -> Unit
) {

    val dialogButtonColor by animateColorAsState(
        if (!isFix)
            QQCleanerColorTheme.colors.dialogButtonDefault
        else
            QQCleanerColorTheme.colors.dialogButtonPress,
        tween(600)
    )

    val dialogButtonTextColor by animateColorAsState(
        if (!isFix)
            QQCleanerColorTheme.colors.dialogButtonTextDefault
        else
            QQCleanerColorTheme.colors.dialogButtonTextPress,
        tween(600)
    )
    Row(
        modifier = Modifier
            .padding(start = 24.dp, top = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = dialogButtonColor,
                shape = QQCleanerShapes.dialogButtonBackground
            )
            .clip(shape = QQCleanerShapes.dialogButtonBackground)
            .clickable(enabled = isFix, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier,
            style = QQCleanerTypes.DialogButtonStyle,
            color = dialogButtonTextColor,
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.cancel)
        )
    }
}