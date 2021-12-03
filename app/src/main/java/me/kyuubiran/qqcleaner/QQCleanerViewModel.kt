package me.kyuubiran.qqcleaner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme

class QQCleanerViewModel : ViewModel() {
    var theme by mutableStateOf(QQCleanerColorTheme.Theme.Light)

    var statusBarHeight by mutableStateOf(0.dp)

}