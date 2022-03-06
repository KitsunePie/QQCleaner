package me.kyuubiran.qqcleaner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.getCurrentTheme
import me.kyuubiran.qqcleaner.util.ConfigManager.sIsBlackTheme

object QQCleanerData {

    var theme by mutableStateOf(getCurrentTheme())

    var navigationBarHeight by mutableStateOf(0.dp)

    var isDark by mutableStateOf(false)

    var isBlack by mutableStateOf(sIsBlackTheme)

    var isFirst by mutableStateOf(true)
}