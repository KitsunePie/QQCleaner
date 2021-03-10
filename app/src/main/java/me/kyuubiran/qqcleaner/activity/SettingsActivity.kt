package me.kyuubiran.qqcleaner.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.*
import me.kyuubiran.qqcleaner.BuildConfig
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.hostApp
import me.kyuubiran.qqcleaner.dialog.*
import me.kyuubiran.qqcleaner.dialog.CleanDialog.showConfirmDialog
import me.kyuubiran.qqcleaner.utils.*
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_AUTO_CLEAN_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CLEAN_DELAY
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CURRENT_CLEANED_TIME
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CUSTOMER_CLEAN_LIST
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_TOTAL_CLEANED_SIZE
import me.kyuubiran.qqcleaner.utils.ConfigManager.checkCfg
import me.kyuubiran.qqcleaner.utils.ConfigManager.getConfig
import me.kyuubiran.qqcleaner.utils.ConfigManager.getInt
import me.kyuubiran.qqcleaner.utils.ConfigManager.getLong
import me.kyuubiran.qqcleaner.utils.ConfigManager.setConfig
import java.text.SimpleDateFormat

class SettingsActivity : AppCompatTransferActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Ftb)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkCfg()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        //延迟初始化Preference
        private lateinit var autoClean: SwitchPreferenceCompat
        private lateinit var cleanedHistory: Preference
        private lateinit var autoCleanMode: ListPreference
        private lateinit var cleanedTime: Preference
        private lateinit var cleanDelay: Preference
        private lateinit var halfClean: Preference
        private lateinit var fullClean: Preference
        private lateinit var customerCleanList: MultiSelectListPreference
        private lateinit var doCustomerClean: Preference
        private lateinit var supportMe: Preference
        private lateinit var gotoGithub: Preference
        private lateinit var joinQQGroup: Preference
        private lateinit var joinTelegram: Preference
        private lateinit var moduleInfo: Preference

        //重置清理时间计数器
        private var clicked = 0

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            //初始化
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            autoClean = findPreference("AutoClean")!!
            cleanedHistory = findPreference("CleanedHistory")!!
            autoCleanMode = findPreference("AutoCleanMode")!!
            cleanedTime = findPreference("CleanedTime")!!
            cleanDelay = findPreference("CleanDelay")!!
            halfClean = findPreference("HalfClean")!!
            fullClean = findPreference("FullClean")!!
            customerCleanList = findPreference("CustomerClean")!!
            doCustomerClean = findPreference("DoCustomerClean")!!
            gotoGithub = findPreference("GotoGithub")!!
            supportMe = findPreference("SupportMe")!!
            joinQQGroup = findPreference("JoinQQGroup")!!
            joinTelegram = findPreference("JoinTelegram")!!
            moduleInfo = findPreference("ModuleInfo")!!
            init()
        }

        //初始化函数
        private fun init() {
            initSummary()
            toggleCleanedTimeShow()
            setClickable()
            setVersionName()
            setCustomerCleanList()
            setConfig(CFG_CUSTOMER_CLEAN_LIST, customerCleanList.values)
        }

        private fun setCustomerCleanList() {
            when (hostApp) {
                HostApp.QQ -> {
                    customerCleanList.setEntries(R.array.qq_customer_clean_list)
                    customerCleanList.setEntryValues(R.array.qq_customer_clean_list_values)
                }
                HostApp.TIM -> {
                    customerCleanList.setEntries(R.array.qq_customer_clean_list)
                    customerCleanList.setEntryValues(R.array.qq_customer_clean_list_values)
                }
                HostApp.WE_CHAT -> {
                    customerCleanList.setEntries(R.array.we_chat_customer_clean_list)
                    customerCleanList.setEntryValues(R.array.we_chat_customer_clean_list_values)
                }
            }
        }

        //设置Item点击事件
        private fun setClickable() {
            halfClean.setOnPreferenceClickListener {
                showConfirmDialog(HALF_MODE, this.requireContext())
                true
            }
            fullClean.setOnPreferenceClickListener {
                showConfirmDialog(FULL_MODE, this.requireContext())
                true
            }
            customerCleanList.setOnPreferenceChangeListener { _, newValue ->
                try {
                    setConfig(CFG_CUSTOMER_CLEAN_LIST, newValue)
                    appContext?.makeToast("好耶 保存自定义瘦身列表成功了!")
                } catch (e: Exception) {
                    loge(e)
                }
                true
            }
            doCustomerClean.setOnPreferenceClickListener {
                showConfirmDialog(CUSTOMER_MODE, this.requireContext())
                true
            }
            gotoGithub.setOnPreferenceClickListener {
                openUrl("https://github.com/KyuubiRan/QQCleaner")
                appContext?.makeToast("喜欢的话给我点个小星星吧~")
                true
            }
            joinQQGroup.setOnPreferenceClickListener {
                openUrl("https://jq.qq.com/?_wv=1027&k=VnwmAAGA")
                true
            }
            joinTelegram.setOnPreferenceClickListener {
                openUrl("https://t.me/QQCleanerCh")
                true
            }
            supportMe.setOnPreferenceClickListener {
                SupportMeDialog.showSupportMeDialog(this.requireContext())
                true
            }
            cleanedTime.setOnPreferenceClickListener {
                if (clicked < 6) {
                    clicked++
                    if (clicked > 3) {
                        appContext?.makeToast(
                            "再点${7 - clicked}次重置清理时间"
                        )
                    }
                } else {
                    clicked = 0
                    setConfig(CFG_CURRENT_CLEANED_TIME, 0)
                    cleanedTime.setSummary(R.string.no_cleaned_his_hint)
                    appContext?.makeToast("已重置清理时间")
                }
                true
            }
            cleanedHistory.setOnPreferenceClickListener {
                appContext?.makeToast("已刷新统计信息")
                initSummary()
                true
            }
            cleanDelay.setOnPreferenceClickListener {
                CleanDialog.showCleanDelayDialog(this.requireContext(), autoClean)
                true
            }
        }

        private fun openUrl(url: String) {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        //切换自动瘦身时间是否显示
        private fun toggleCleanedTimeShow() {
            val currentCleanedTime = getLong(CFG_CURRENT_CLEANED_TIME)
            setConfig(CFG_AUTO_CLEAN_ENABLED, autoClean.isChecked)
            if (currentCleanedTime == 0L) {
                cleanedTime.setSummary(R.string.no_cleaned_his_hint)
            } else {
                try {
                    val format = SimpleDateFormat.getInstance()
                    cleanedTime.summary = format.format(currentCleanedTime)
                } catch (e: Exception) {
                    loge(e)
                    cleanedTime.summary = "喵喵喵"
                }
            }
            cleanedTime.isVisible = autoClean.isChecked
            autoCleanMode.isVisible = autoClean.isChecked
            cleanDelay.isVisible = autoClean.isChecked
            autoClean.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    cleanedTime.isVisible = newValue as Boolean
                    autoCleanMode.isVisible = newValue
                    cleanDelay.isVisible = newValue
                    setConfig(CFG_AUTO_CLEAN_ENABLED, newValue)
                    true
                }
        }

        private fun initSummary() {
            if (getConfig(CFG_TOTAL_CLEANED_SIZE) != 0) {
                cleanedHistory.summary =
                    "总共为您腾出:${formatSize(getLong(CFG_TOTAL_CLEANED_SIZE))}空间"
            } else {
                cleanedHistory.setSummary(R.string.no_cleaned_his_hint)
            }

            val delayTmp = getInt(CFG_CLEAN_DELAY)
            autoClean.summary = if (delayTmp == 0) "当前清理的间隔为24小时" else "当前清理的间隔为${delayTmp}小时"
        }

        private fun setVersionName() {
            moduleInfo.summary = BuildConfig.VERSION_NAME
        }
    }
}
