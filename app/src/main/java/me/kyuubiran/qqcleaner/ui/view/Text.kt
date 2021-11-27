package me.kyuubiran.qqcleaner.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.itemTipType
import me.kyuubiran.qqcleaner.ui.theme.itemType
import me.kyuubiran.qqcleaner.ui.theme.subtitleType

/**
 * 这里存放着各种 Text 控件
 */

/**
 * 菜单的 Text()
 * @param text 对应文本
 */
@Composable
fun ItemText(text: String) {
    ItemText(text = text, modifier = Modifier)
}

/**
 * 菜单的 Text()
 * @param text 对应文本
 * @param modifier 对应 modifier 参数
 */
@Composable
fun ItemText(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = itemType,
        color = QQCleanerColorTheme.colors.titleTextColor,
        modifier = modifier
    )
}

/**
 * 菜单 tip 的 Text()
 * @param text 对应文本
 * @param modifier 对应 modifier 参数
 */
@Composable
fun ItemTipText(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = itemTipType,
        color = QQCleanerColorTheme.colors.titleTextColor,
        modifier = modifier
    )
}

/**
 * 副标题的 Text()
 * @param text 对应文本
 */
@Composable
fun SubTitleText(text: String) {
    SubTitleText(text = text, modifier = Modifier)
}

/**
 * 副标题的 Text()
 * @param text 对应文本
 * @param modifier 对应 modifier 参数
 */
@Composable
fun SubTitleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        color = QQCleanerColorTheme.colors.titleTextColor,
        style = subtitleType,
        modifier = modifier
    )
}

/**
 * 标题的 Text()
 * @param text 对应文本
 */
@Composable
fun TitleText(text: String) {
    TitleText(
        text = text,
        modifier = Modifier
    )
}

/**
 * 标题的 Text()
 * @param text 对应文本
 * @param modifier 对应 modifier 参数
 */
@Composable
fun TitleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        color = QQCleanerColorTheme.colors.titleTextColor,
        style = subtitleType,
        modifier = modifier
    )
}