package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates
import me.kyuubiran.qqcleaner.databinding.ThemeDialogBinding

class ThemeDialog(states: MainActivityStates): BaseDialog(states) {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = ThemeDialogBinding.inflate(layoutInflater)
        layout = binding.root

        lifecycleScope.launch {
            states.theme.collect {
                binding.topDivider.setBackgroundColor(it.dividerColor)
                TextViewCompat.setCompoundDrawableTintList(binding.lightTheme, ColorStateList.valueOf(it.mainThemeColor))
                binding.bottomDivider.setBackgroundColor(it.dividerColor)
            }
        }

        return super.onCreateDialog(savedInstanceState)
    }

}