package me.kyuubiran.qqcleaner.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import me.kyuubiran.qqcleaner.BuildConfig
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.hostApp
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.dialog.*
import me.kyuubiran.qqcleaner.dialog.CleanDialog.showConfirmDialog
import me.kyuubiran.qqcleaner.dialog.CleanDialog.showSetFileDateLimitDialog
import me.kyuubiran.qqcleaner.utils.*
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_AUTO_CLEAN_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CLEAN_DELAY
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CURRENT_CLEANED_TIME
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CUSTOMER_CLEAN_LIST
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_DATE_LIMIT
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_DATE_LIMIT_ENABLED
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
        val mode = if (isInNightMode()) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        AppCompatDelegate.setDefaultNightMode(mode)
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

        private lateinit var powerMode: SwitchPreferenceCompat
        private lateinit var enableDateLimit: SwitchPreferenceCompat
        private lateinit var setDateLimit: Preference

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

            powerMode = findPreference("PowerMode")!!
            enableDateLimit = findPreference("EnableDateLimit")!!
            setDateLimit = findPreference("SetDateLimit")!!

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
            toggleSwitchItemCtrl()
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
            setDateLimit.setOnPreferenceClickListener {
                showSetFileDateLimitDialog(this.requireContext(), it)
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
                openQQGroup("827356240")
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

        private fun  openQQGroup(uin: String) {
            val uri = Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=$uin&card_type=group&source=qrcode")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            when (hostApp) {
                HostApp.QQ, HostApp.TIM -> {
                    intent.setPackage(hostInfo.packageName)
                }
                else -> Unit
            }
            startActivity(intent)
        }

        private fun toggleSwitchItemCtrl() {
            //自动瘦身是否显示
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
                    autoClean.summary =
                        if (newValue) "当前清理的间隔为${getInt(CFG_CLEAN_DELAY, 24)}小时" else "未开启"
                    true
                }
            //设置清理超过日期是否显示
            setDateLimit.isVisible = enableDateLimit.isChecked
            enableDateLimit.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    setDateLimit.isVisible = newValue as Boolean
                    setConfig(CFG_DATE_LIMIT_ENABLED, newValue)
                    true
                }
        }

        private fun initSummary() {
            //腾出空间
            if (getConfig(CFG_TOTAL_CLEANED_SIZE) != 0) {
                cleanedHistory.summary =
                    "总共为您腾出:${formatSize(getLong(CFG_TOTAL_CLEANED_SIZE))}空间"
            } else {
                cleanedHistory.setSummary(R.string.no_cleaned_his_hint)
            }
            //自动瘦身
            autoClean.summary =
                if (autoClean.isVisible) "当前清理的间隔为${getInt(CFG_CLEAN_DELAY, 24)}小时" else "未开启"
            //设置清理超过日期
            setDateLimit.summary = "当前会清理存在超过${getInt(CFG_DATE_LIMIT, 3)}天的文件"
        }

        private fun setVersionName() {
            moduleInfo.summary = "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
        }
    }
}
