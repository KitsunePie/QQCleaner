package me.kyuubiran.qqcleaner.dialog

import android.app.Dialog
import android.os.Bundle
import me.kyuubiran.qqcleaner.MainActivity.MainActivityStates
import me.kyuubiran.qqcleaner.databinding.ThemeDialogBinding

class ThemeDialog(states: MainActivityStates): BaseDialog(states) {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        layout = ThemeDialogBinding.inflate(layoutInflater).root
        return super.onCreateDialog(savedInstanceState)
    }

}