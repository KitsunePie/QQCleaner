package me.kyuubiran.qqcleaner.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.view.CardGroup
import me.kyuubiran.qqcleaner.ui.view.SwitchItem

class ModuleActivity : BaseActivity() {

    @Composable
    private fun MainUI() {
        CardGroup(title = R.string.clean_settings_title) {
            SwitchItem(title = R.string.auto_clean_title)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainUI()
            }
        }
    }
}