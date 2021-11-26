package me.kyuubiran.qqcleaner.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.kyuubiran.qqcleaner.QQCleanerViewModel
import me.kyuubiran.qqcleaner.ui.ItemText
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme

@Composable
fun ItemCard(text: String, @DrawableRes id: Int, contentDescription: String) {
    val viewModel: QQCleanerViewModel = viewModel()
    Row(modifier = Modifier
        .height(56.dp)
        .clickable {
            viewModel.theme = when (viewModel.theme) {
                QQCleanerColorTheme.Theme.Light -> QQCleanerColorTheme.Theme.Dark
                QQCleanerColorTheme.Theme.Dark -> QQCleanerColorTheme.Theme.Light
            }
            viewModel.navigationBarLight.value = when (viewModel.theme) {
                QQCleanerColorTheme.Theme.Light -> true
                QQCleanerColorTheme.Theme.Dark -> false
            }
            viewModel.statusBarLight.value = when (viewModel.theme) {
                QQCleanerColorTheme.Theme.Light -> true
                QQCleanerColorTheme.Theme.Dark -> false
            }
        }
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        ItemText(text = text, Modifier.weight(1f))
        Icon(painter = painterResource(id = id), contentDescription = contentDescription)

    }
}
