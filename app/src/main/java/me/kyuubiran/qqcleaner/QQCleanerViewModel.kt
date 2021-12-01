package me.kyuubiran.qqcleaner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.kyuubiran.qqcleaner.ui.theme.Locales
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme

class QQCleanerViewModel : ViewModel() {
    var theme by mutableStateOf(QQCleanerColorTheme.Theme.Light)

    var locales by mutableStateOf(Locales.CN)

    val statusBarLight: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val navigationBarLight: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}