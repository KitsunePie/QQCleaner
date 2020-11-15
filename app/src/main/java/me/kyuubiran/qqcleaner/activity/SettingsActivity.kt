package me.kyuubiran.qqcleaner.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.*
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.dialog.CUSTOMER_MODE
import me.kyuubiran.qqcleaner.dialog.CleanDialog.showConfirmDialog
import me.kyuubiran.qqcleaner.dialog.FULL_MODE
import me.kyuubiran.qqcleaner.dialog.HALF_MODE
import me.kyuubiran.qqcleaner.dialog.SupportMeDialog
import me.kyuubiran.qqcleaner.utils.*
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_AUTO_CLEAN_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CURRENT_CLEANED_TIME
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CUSTOMER_CLEAN_LIST
import java.lang.Exception
import java.text.SimpleDateFormat


class SettingsActivity : AppCompatTransferActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Ftb)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private lateinit var autoClean: SwitchPreferenceCompat
        private lateinit var autoCleanMode: ListPreference
        private lateinit var cleanedTime: Preference
        private lateinit var halfClean: Preference
        private lateinit var fullClean: Preference
        private lateinit var customerCleanList: MultiSelectListPreference
        private lateinit var doCustomerClean: Preference
        private lateinit var supportMe: Preference
        private lateinit var gotoGithub: Preference

        var clicked = 0

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            autoClean = findPreference("AutoClean")!!
            autoCleanMode = findPreference("AutoCleanMode")!!
            cleanedTime = findPreference("CleanedTime")!!
            halfClean = findPreference("HalfClean")!!
            fullClean = findPreference("FullClean")!!
            customerCleanList = findPreference("CustomerClean")!!
            doCustomerClean = findPreference("DoCustomerClean")!!
            gotoGithub = findPreference("GotoGithub")!!
            supportMe = findPreference("SupportMe")!!
            init()
        }

        private fun init() {
            toggleCleanedTimeShow()
            setClickable()
        }

        private fun setClickable() {
            halfClean.setOnPreferenceClickListener {
                onClickCleanHalf()
                true
            }
            fullClean.setOnPreferenceClickListener {
                onClickCleanFull()
                true
            }
            customerCleanList.setOnPreferenceChangeListener { _, newValue ->
                try {
                    ConfigManager.setConfig(CFG_CUSTOMER_CLEAN_LIST, newValue)
                    qqContext?.showToastBySystem("好耶 保存自定义瘦身列表成功了!")
                } catch (e: Exception) {
                    loge(e)
                }
                true
            }
            doCustomerClean.setOnPreferenceClickListener {
                showConfirmDialog(CUSTOMER_MODE, this.activity!!)
                true
            }
            gotoGithub.setOnPreferenceClickListener {
                val uri = Uri.parse("https://github.com/KyuubiRan/QQCleaner")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                qqContext?.showToastBySystem("喜欢的话给我点个小星星吧~")
                true
            }
            supportMe.setOnPreferenceClickListener {
                SupportMeDialog.showSupportMeDialog(this.activity!!)
                true
            }
            cleanedTime.setOnPreferenceClickListener {
                if (clicked < 6) {
                    clicked++
                    if (clicked > 3) {
                        qqContext?.showToastBySystem(
                            "再点${7 - clicked}次重置清理时间"
                        )
                    }
                } else {
                    clicked = 0
                    ConfigManager.setConfig(CFG_CURRENT_CLEANED_TIME, 0)
                    cleanedTime.setSummary(R.string.auto_clean_time_hint)
                    qqContext?.showToastBySystem("已重置清理时间")
                }
                true
            }
        }

        private fun onClickCleanHalf() {
            showConfirmDialog(HALF_MODE, this.activity!!)
        }

        private fun onClickCleanFull() {
            showConfirmDialog(FULL_MODE, this.activity!!)
        }

        private fun toggleCleanedTimeShow() {
            if (ConfigManager.getLong(CFG_CURRENT_CLEANED_TIME).toString() == "null" ||
                ConfigManager.getLong(CFG_CURRENT_CLEANED_TIME) == 0L
            ) {
                cleanedTime.setSummary(R.string.auto_clean_time_hint)
            } else {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                cleanedTime.summary = format.format(ConfigManager.getLong(CFG_CURRENT_CLEANED_TIME))
            }
            cleanedTime.isVisible = autoClean.isChecked
            autoCleanMode.isVisible = autoClean.isChecked
            autoClean.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    cleanedTime.isVisible = newValue as Boolean
                    autoCleanMode.isVisible = newValue
                    ConfigManager.setConfig(CFG_AUTO_CLEAN_ENABLED, newValue)
                    true
                }
        }
    }
}
