package me.kyuubiran.qqcleaner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.getCurrentTheme

object QQCleanerData {

    var theme by mutableStateOf(getCurrentTheme())

    var statusBarHeight by mutableStateOf(0.dp)

    var navigationBarHeight by mutableStateOf(0.dp)

    var isDark by mutableStateOf(false)

    var isFirst by mutableStateOf(true)
}