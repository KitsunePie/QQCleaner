package me.kyuubiran.qqcleaner.ui.view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import me.kyuubiran.qqcleaner.ui.composable.Item
import me.kyuubiran.qqcleaner.ui.composable.Switch

@ExperimentalMaterialApi
@Composable
fun SwitchItem(
    text: String,
    checked: MutableState<Boolean>,
    onClick: ((Boolean) -> Unit)? = null,
    clickNoToggle: Boolean = false
) {
    fun toggle() {
        if (onClick == null) {
            checked.value = !checked.value
        } else {
            if (!clickNoToggle) checked.value = !checked.value
            onClick(checked.value)
        }
    }

    Item(text = text, onClick = { toggle() }) {
        Switch(checked = checked, onCheckedChange = { if (it != checked.value) toggle() })
    }
}


