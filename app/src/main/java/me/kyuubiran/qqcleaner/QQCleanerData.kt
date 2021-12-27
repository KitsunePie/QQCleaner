package me.kyuubiran.qqcleaner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.System

object QQCleanerData {
    // 当前主题
    var theme by mutableStateOf(System)

    var statusBarHeight by mutableStateOf(0.dp)

    var navigationBarHeight by mutableStateOf(0.dp)
}