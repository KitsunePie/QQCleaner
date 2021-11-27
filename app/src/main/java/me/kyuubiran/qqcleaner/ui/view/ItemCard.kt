package me.kyuubiran.qqcleaner.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.ItemText
import me.kyuubiran.qqcleaner.ui.ItemTipText

@Composable
fun ItemCard(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

/**
 * 带 Icon 的 item
 *
 * @param text 文本
 * @param contentDescription 无障碍文本
 * @param id 对应图片的 id
 * @param height 对应高度
 * @param width 对应宽度
 */

@Composable
fun ItemIconCard(
    text: String,
    contentDescription: String,
    @DrawableRes id: Int,
    height: Dp,
    width: Dp? = height
) {
    ItemCard {
        ItemText(text = text, Modifier.weight(1f))
        Icon(
            painter = painterResource(id = id),
            contentDescription = contentDescription,
            modifier = Modifier
                .height(height)
                .width(width ?: height)
        )
    }
}

/**
 * 菜单 Item，图标为前进符号
 *
 * @param text 文本
 * @param contentDescription 无障碍文本
 */
@Composable
fun ItemMenuCard(text: String, contentDescription: String) {
    ItemIconCard(
        text = text,
        contentDescription = contentDescription,
        id = R.drawable.ic_baseline_arrow_forward,
        height = 24.dp
    )
}

/**
 * 文本 Item
 *
 * @param text 文本
 * @param tipText 提示文本
 */
@Composable
fun ItemTextCard(text: String, tipText: String) {
    ItemCard {
        ItemText(text = text, Modifier.weight(1f))
        ItemTipText(text = tipText, Modifier)
    }
}

/**
 * 开关 Item，图标为开关
 *
 * @param text 文本
 * @param contentDescription 无障碍文本
 */
@Composable
fun ItemSwitchCard(text: String, contentDescription: String) {
    ItemIconCard(
        text = text,
        contentDescription = contentDescription,
        id = R.drawable.ic_baseline_toggle_on,
        height = 24.dp,
        width = 36.dp
    )
}

/**
 * 更多 Item，图标为更多文本
 *
 * @param text 文本
 * @param contentDescription 无障碍文本
 */
@Composable
fun ItemMoreCard(text: String, contentDescription: String) {
    ItemIconCard(
        text = text,
        contentDescription = contentDescription,
        id = R.drawable.ic_baseline_more,
        height = 24.dp
    )

}