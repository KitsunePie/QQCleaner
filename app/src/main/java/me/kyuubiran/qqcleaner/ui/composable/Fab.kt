package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.QQCleanerData
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes
import me.kyuubiran.qqcleaner.ui.util.drawColoredShadow

@Composable
fun Fab(modifier: Modifier, text: String, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .padding(QQCleanerData.navigationBarHeight + 16.dp)
            .width(98.dp)
            .height(35.dp)
            .drawColoredShadow(
                colors.mainThemeColor,
                0.3f,
                shadowRadius = 30.dp,
                offsetX = 0.dp,
                offsetY = (3).dp
            )
            .clip(RoundedCornerShape(80.dp))
            .background(
                colors.mainThemeColor,
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = QQCleanerTypes.cleanerTextStyle,
            color = colors.whiteColor
        )
    }
}