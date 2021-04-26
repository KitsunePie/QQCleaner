@file:Suppress("DEPRECATION")

package me.kyuubiran.qqcleaner.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.*
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.BuildConfig
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.hostApp
import me.kyuubiran.qqcleaner.data.hostInfo
import me.kyuubiran.qqcleaner.dialog.*
import me.kyuubiran.qqcleaner.dialog.CleanDialog.showConfirmDialog
import me.kyuubiran.qqcleaner.dialog.CleanDialog.showSetFileDateLimitDialog
import me.kyuubiran.qqcleaner.utils.CleanManager.CUSTOMER_MODE
import me.kyuubiran.qqcleaner.utils.CleanManager.FULL_MODE
import me.kyuubiran.qqcleaner.utils.CleanManager.HALF_MODE
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_AUTO_CLEAN_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_AUTO_CLEAN_MODE
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CLEAN_DELAY
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CURRENT_CLEANED_TIME
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_CUSTOMER_CLEAN_LIST
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_DATE_LIMIT
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_DATE_LIMIT_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_DO_NOT_DISTURB_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_POWER_MODE_ENABLED
import me.kyuubiran.qqcleaner.utils.ConfigManager.CFG_TOTAL_CLEANED_SIZE
import me.kyuubiran.qqcleaner.utils.ConfigManager.checkCfg
import me.kyuubiran.qqcleaner.utils.ConfigManager.getBool
import me.kyuubiran.qqcleaner.utils.ConfigManager.getInt
import me.kyuubiran.qqcleaner.utils.ConfigManager.getJsonArray
import me.kyuubiran.qqcleaner.utils.ConfigManager.getLong
import me.kyuubiran.qqcleaner.utils.ConfigManager.getString
import me.kyuubiran.qqcleaner.utils.ConfigManager.setConfig
import me.kyuubiran.qqcleaner.utils.ConfigManager.setJsonArray
import me.kyuubiran.qqcleaner.utils.ConfigManager.toHashSet
import me.kyuubiran.qqcleaner.utils.HostApp
import me.kyuubiran.qqcleaner.utils.formatSize
import me.kyuubiran.qqcleaner.utils.isInNightMode
import me.kyuubiran.qqcleaner.utils.show
import java.text.SimpleDateFormat

class SettingsActivity : Activity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        if (isInNightMode()) {
            setTheme(R.style.AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        fragmentManager.beginTransaction().replace(R.id.settings, SettingsFragment()).commit()
        checkCfg()
    }

    class SettingsFragment : PreferenceFragment() {
        //延迟初始化Preference
        private lateinit var autoClean: SwitchPreference
        private lateinit var cleanedHistory: Preference
        private lateinit var autoCleanMode: ListPreference
        private lateinit var cleanedTime: Preference
        private lateinit var cleanDelay: Preference

        private lateinit var halfClean: Preference
        private lateinit var fullClean: Preference
        private lateinit var customerCleanList: MultiSelectListPreference
        private lateinit var doCustomerClean: Preference

        private lateinit var powerMode: SwitchPreference
        private lateinit var enableDateLimit: SwitchPreference
        private lateinit var setDateLimit: Preference
        private lateinit var doNotDisturb: SwitchPreference

        private lateinit var supportMe: Preference
        private lateinit var gotoGithub: Preference
        private lateinit var joinQQGroup: Preference
        private lateinit var joinTelegram: Preference
        private lateinit var moduleInfo: Preference

        //重置清理时间计数器
        private var clicked = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            //初始化
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.root_preferences)
            autoClean = findPreference("AutoClean") as SwitchPreference
            cleanedHistory = findPreference("CleanedHistory")!!
            autoCleanMode = findPreference("AutoCleanMode") as ListPreference
            cleanedTime = findPreference("CleanedTime")!!
            cleanDelay = findPreference("CleanDelay")!!

            halfClean = findPreference("HalfClean")!!
            fullClean = findPreference("FullClean")!!
            customerCleanList = findPreference("CustomerClean") as MultiSelectListPreference
            doCustomerClean = findPreference("DoCustomerClean")!!

            powerMode = findPreference("PowerMode") as SwitchPreference
            enableDateLimit = findPreference("EnableDateLimit") as SwitchPreference
            setDateLimit = findPreference("SetDateLimit")!!
            doNotDisturb = findPreference("DoNotDisturb") as SwitchPreference

            gotoGithub = findPreference("GotoGithub")!!
            supportMe = findPreference("SupportMe")!!
            joinQQGroup = findPreference("JoinQQGroup")!!
            joinTelegram = findPreference("JoinTelegram")!!
            moduleInfo = findPreference("ModuleInfo")!!
            init()
        }

        //初始化函数
        private fun init() {
            setCustomerCleanList()
            refreshCleanedSize()
            initItemStatus()
            toggleSwitchItemCtrl()
            setClickable()
            setSummary()
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

        private fun initItemStatus() {
            autoClean.isChecked = getBool(CFG_AUTO_CLEAN_ENABLED)
            powerMode.isChecked = getBool(CFG_POWER_MODE_ENABLED)
            enableDateLimit.isChecked = getBool(CFG_DATE_LIMIT_ENABLED)
            doNotDisturb.isChecked = getBool(CFG_DO_NOT_DISTURB_ENABLED)
            autoCleanMode.value = getString(CFG_AUTO_CLEAN_MODE, HALF_MODE)
            customerCleanList.values = getJsonArray(CFG_CUSTOMER_CLEAN_LIST)?.toHashSet<String>()
        }

        //设置Item点击事件
        private fun setClickable() {
            halfClean.setOnPreferenceClickListener {
                showConfirmDialog(HALF_MODE_INT, it.context)
                true
            }
            fullClean.setOnPreferenceClickListener {
                showConfirmDialog(FULL_MODE_INT, it.context)
                true
            }
            customerCleanList.setOnPreferenceChangeListener { _, newValue ->
                try {
                    @Suppress("UNCHECKED_CAST")
                    setJsonArray(CFG_CUSTOMER_CLEAN_LIST, newValue as HashSet<String>)
                    appContext.show("好耶 保存自定义瘦身列表成功了!")
                } catch (e: Exception) {
                    Log.e(e)
                }
                true
            }
            setDateLimit.setOnPreferenceClickListener {
                showSetFileDateLimitDialog(it.context, it)
                true
            }

            doCustomerClean.setOnPreferenceClickListener {
                showConfirmDialog(CUSTOMER_MODE_INT, it.context)
                true
            }
            gotoGithub.setOnPreferenceClickListener {
                openUrl("https://github.com/KyuubiRan/QQCleaner")
                appContext.show("喜欢的话给我点个小星星吧~")
                true
            }
            joinQQGroup.setOnPreferenceClickListener {
                openQQGroup()
                true
            }
            joinTelegram.setOnPreferenceClickListener {
                openUrl("https://t.me/QQCleaner")
                true
            }
            supportMe.setOnPreferenceClickListener {
                SupportMeDialog.showSupportMeDialog(it.context)
                true
            }
            cleanedTime.setOnPreferenceClickListener {
                if (clicked < 6) {
                    clicked++
                    if (clicked > 3) {
                        appContext.show(
                            "再点${7 - clicked}次重置清理时间"
                        )
                    }
                } else {
                    clicked = 0
                    setConfig(CFG_CURRENT_CLEANED_TIME, 0)
                    cleanedTime.setSummary(R.string.no_cleaned_his_hint)
                    appContext.show("已重置清理时间")
                }
                true
            }
            cleanedHistory.setOnPreferenceClickListener {
                appContext.show("已刷新统计信息")
                refreshCleanedSize()
                true
            }
            cleanDelay.setOnPreferenceClickListener {
                CleanDialog.showCleanDelayDialog(it.context, autoClean)
                true
            }
            autoCleanMode.setOnPreferenceChangeListener { _, newValue ->
                when (newValue) {
                    HALF_MODE -> setConfig(CFG_AUTO_CLEAN_MODE, HALF_MODE)
                    FULL_MODE -> setConfig(CFG_AUTO_CLEAN_MODE, FULL_MODE)
                    CUSTOMER_MODE -> setConfig(CFG_AUTO_CLEAN_MODE, CUSTOMER_MODE)
                }
                true
            }
        }

        private fun openUrl(url: String) {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        private fun openQQGroup(uin: String = "827356240") {
            val uri =
                Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=$uin&card_type=group&source=qrcode")
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
                    Log.e(e)
                    cleanedTime.summary = "喵喵喵"
                }
            }
            cleanedTime.isEnabled = autoClean.isChecked
            autoCleanMode.isEnabled = autoClean.isChecked
            cleanDelay.isEnabled = autoClean.isChecked
            autoClean.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    cleanedTime.isEnabled = newValue as Boolean
                    autoCleanMode.isEnabled = newValue
                    cleanDelay.isEnabled = newValue
                    setConfig(CFG_AUTO_CLEAN_ENABLED, newValue)
                    autoClean.summary =
                        if (newValue) "当前清理的间隔为${getInt(CFG_CLEAN_DELAY, 24)}小时" else "未开启"
                    true
                }
            //设置清理超过日期是否显示
            setDateLimit.isEnabled = enableDateLimit.isChecked
            enableDateLimit.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    setDateLimit.isEnabled = newValue as Boolean
                    setConfig(CFG_DATE_LIMIT_ENABLED, newValue)
                    true
                }
            //设置是否显示自动清理Toast
            doNotDisturb.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    setConfig(CFG_DO_NOT_DISTURB_ENABLED, newValue)
                    true
                }
            //强力模式是否开启
            powerMode.setOnPreferenceChangeListener { _, newValue ->
                setConfig(CFG_POWER_MODE_ENABLED, newValue as Boolean)
                true
            }
        }

        private fun refreshCleanedSize() {
            //腾出空间
            if (getInt(CFG_TOTAL_CLEANED_SIZE) != 0) {
                cleanedHistory.summary =
                    "总共为您腾出:${formatSize(getLong(CFG_TOTAL_CLEANED_SIZE))}空间"
            } else {
                cleanedHistory.setSummary(R.string.no_cleaned_his_hint)
            }
        }

        private fun setSummary() {
            //自动瘦身
            autoClean.summary =
                if (autoClean.isEnabled) "当前清理的间隔为${getInt(CFG_CLEAN_DELAY, 24)}小时" else "未开启"
            //设置清理超过日期
            setDateLimit.summary = "当前会清理存在超过${getInt(CFG_DATE_LIMIT, 3)}天的文件"
            moduleInfo.summary = "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
        }
    }
}
