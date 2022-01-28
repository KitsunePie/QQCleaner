package me.kyuubiran.qqcleaner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme

object QQCleanerData {
    // 当前主题
    var theme by mutableStateOf(QQCleanerColorTheme.getCurrentTheme())

    var statusBarHeight by mutableStateOf(0.dp)

    var navigationBarHeight by mutableStateOf(0.dp)

    var isDark by mutableStateOf(false)

    var isFirst by mutableStateOf(true)
}