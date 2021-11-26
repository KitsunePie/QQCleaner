package me.kyuubiran.qqcleaner.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.SubTitleText
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme

/**
 * 带着标题的的屑布局
 * @param text 对应的标题
 * @param height 卡片的高度
 * @param content 子布局
 */
@Composable
fun TitleCard(text: String, height: Dp, content: @Composable ColumnScope.() -> Unit) {
    Column {
        SubTitleText(
            text = text,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        Column(
            modifier = Modifier
                .height(height)
                .fillMaxWidth()
                .padding(start = 8.dp)
                .background(
                    color = QQCleanerColorTheme.colors.background,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(shape = RoundedCornerShape(10.dp)),
            content = content
        )
    }
}