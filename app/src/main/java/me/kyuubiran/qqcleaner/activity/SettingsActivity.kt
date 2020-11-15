package me.kyuubiran.qqcleaner.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.dialog.CUSTOMER_MODE
import me.kyuubiran.qqcleaner.dialog.CleanDialog.showConfirmDialog
import me.kyuubiran.qqcleaner.dialog.FULL_MODE
import me.kyuubiran.qqcleaner.dialog.HALF_MODE
import me.kyuubiran.qqcleaner.utils.CleanManager
import me.kyuubiran.qqcleaner.utils.qqContext
import me.kyuubiran.qqcleaner.utils.showToastBySystem


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
        private lateinit var cleanedTime: Preference
        private lateinit var halfClean: Preference
        private lateinit var fullClean: Preference
        private lateinit var customerCleanList: MultiSelectListPreference
        private lateinit var doCustomerClean: Preference
        private lateinit var supportMe: Preference
        private lateinit var gotoGithub: Preference

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            autoClean = findPreference("AutoClean")!!
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
            customerCleanList.setOnPreferenceChangeListener { _, _ ->
                true
            }
            doCustomerClean.setOnPreferenceClickListener {
                CleanManager.customerList = customerCleanList.values
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
                val urlCode = "fkx10658svai9rgm46mk9de"
                val intent = Intent.parseUri(
                    "intent://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s%3Dweb-other&_t=1472443966571#Intent;scheme=alipayqr;package=com.eg.android.AlipayGphone;end".replace(
                        "{urlCode}",
                        urlCode
                    ), 1
                )
                startActivity(intent)
                qqContext?.showToastBySystem("感谢资瓷> <")
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
            cleanedTime.isVisible = autoClean.isChecked
            autoClean.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    qqContext?.showToastBySystem("还在制作中> <")
                    cleanedTime.isVisible = newValue as Boolean
                    true
                }
        }
    }
}
