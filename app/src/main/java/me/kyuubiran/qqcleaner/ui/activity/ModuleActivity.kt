package me.kyuubiran.qqcleaner.ui.activity

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.BuildConfig
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.composable.*
import me.kyuubiran.qqcleaner.util.CleanManager
import me.kyuubiran.qqcleaner.util.ConfigManager.sAutoClean
import me.kyuubiran.qqcleaner.util.ConfigManager.sAutoCleanInterval
import me.kyuubiran.qqcleaner.util.ConfigManager.sLastCleanDate
import me.kyuubiran.qqcleaner.util.jumpUri
import me.kyuubiran.qqcleaner.util.rememberMutableStateOf

class ModuleActivity : BaseActivity() {

    @Composable
    private fun MainUI() {
        val scroller = rememberScrollState()

        val lastCleanDate = rememberMutableStateOf(value = sLastCleanDate)

        //设置自动瘦身间隔对话框
        val autoCleanIntervalText = rememberMutableStateOf(value = "")
        val showAutoCleanIntervalDialog = rememberMutableStateOf(value = false)
        SetAutoCleanIntervalDialog(showAutoCleanIntervalDialog, autoCleanIntervalText)
        //手动执行瘦身对话框
        val showExecuteCleanDialog = rememberMutableStateOf(value = false)
        SetExecuteCleanDialog(showExecuteCleanDialog, lastCleanDate)


        //主UI
        Column {
            AppBar(text = R.string.app_name)

            Column(
                modifier = Modifier.scrollable(
                    state = scroller,
                    orientation = Orientation.Vertical
                )
            ) {
                //瘦身设置
                CardGroup(title = R.string.clean_settings_title) {
                    //自动瘦身
                    SwitchItem(
                        title = R.string.auto_clean_title,
                        checked = rememberMutableStateOf(sAutoClean),
                        onChange = { sAutoClean = it }
                    )
                    //自动瘦身间隔
                    ClickableItem(
                        title = R.string.set_auto_clean_interval_title,
                        desc = R.string.set_auto_clean_interval_desc,
                        onClick = {
                            showAutoCleanIntervalDialog.value = true
                            autoCleanIntervalText.value = sAutoCleanInterval.toString()
                        },
                        showArrow = true
                    )
                    //配置瘦身
                    ClickableItem(
                        title = R.string.modify_config_title,
                        onClick = {
                            startActivity(
                                Intent(
                                    this@ModuleActivity,
                                    ModifyConfigActivity::class.java
                                )
                            )
                        },
                        showArrow = true
                    )
                    //执行瘦身
                    ClickableItem(
                        title = R.string.execute_clean_title,
                        desc = R.string.execute_clean_desc,
                        onClick = {
                            showExecuteCleanDialog.value = true
                        },
                        showArrow = true
                    )
                }
                //瘦身详情
                CardGroup(title = R.string.clean_info_title) {
                    var clickTimes = 0
                    //上次清理
                    ClickableItem(
                        title = stringResource(id = R.string.last_clean_date_title),
                        desc = if (lastCleanDate.value == 0L) stringResource(
                            id = R.string.no_last_clean_date_record
                        ) else SimpleDateFormat.getInstance().format(lastCleanDate.value),
                        onClick = {
                            if (++clickTimes < 7) {
                                if (clickTimes > 3) {
                                    Log.toast(
                                        getString(R.string.click_to_reset_clean_date_with_times).format(
                                            7 - clickTimes
                                        )
                                    )
                                }
                            } else {
                                clickTimes = 0
                                sLastCleanDate = 0L
                                lastCleanDate.value = 0L
                                Log.toast(getString(R.string.reset_clean_date))
                            }
                        }
                    )

//                    val totalCleanSize = rememberMutableStateOf(value = getFormatCleanedSize())
//                    //总计清理
//                    ClickableItem(
//                        title = stringResource(id = R.string.total_cleaned_title),
//                        desc = totalCleanSize.value,
//                        onClick = {
//                            //TODO("ConfigManager.sTotalCleaned不会实时刷新")
//                            totalCleanSize.value = getFormatCleanedSize()
//                            Log.toast(getString(R.string.refresh_clean_size))
//                        }
//                    )
                }
                //关于
                CardGroup(title = R.string.about_title) {
                    //模块版本
                    ClickableItem(
                        title = stringResource(id = R.string.module_version_title),
                        desc = "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
                    )
                    //前往Github
                    ClickableItem(
                        title = R.string.goto_github_title,
                        desc = R.string.goto_github_desc,
                        onClick = {
                            jumpUri("https://github.com/KyuubiRan/QQCleaner")
                        },
                        showArrow = true
                    )
                    //加入Telegram
                    ClickableItem(
                        title = R.string.join_tg_channel_title,
                        desc = R.string.join_tg_channel_desc,
                        onClick = {
                            jumpUri("https://t.me/QQCleaner")
                        },
                        showArrow = true
                    )
                    //加入QQ
                    ClickableItem(
                        title = R.string.join_qq_group_title,
                        desc = R.string.join_qq_group_desc,
                        onClick = {
                            jumpUri("mqqapi://card/show_pslcard?src_type=internal&version=1&uin=827356240&card_type=group&source=qrcode")
                        },
                        showArrow = true
                    )
                }
            }
        }
    }

    @Composable
    private fun SetExecuteCleanDialog(rm: MutableState<Boolean>, cleanDate: MutableState<Long>) {
        Dialog(
            title = R.string.notify,
            text = R.string.execute_clean_dialog_content,
            confirmBtnTitle = R.string.confirm,
            confirm = {
                CleanManager.execute(true)
                System.currentTimeMillis().let {
                    sLastCleanDate = it
                    cleanDate.value = it
                }
            },
            dismissBtnString = R.string.cancel,
            showable = rm
        )
    }

    @Composable
    private fun SetAutoCleanIntervalDialog(rm: MutableState<Boolean>, str: MutableState<String>) {
        Dialog(
            title = stringResource(id = R.string.set_auto_clean_interval_dialog_title),
            content = {
                EditText(
                    text = str.value,
                    onChange = { str.value = it },
                    singleLine = true,
                    kbOpts = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmBtnTitle = stringResource(R.string.save),
            confirm = {
                (str.value.toIntOrNull() ?: 24).let {
                    sAutoCleanInterval = if (it > 0) it else 24
                }
            },
            dismissBtnString = stringResource(id = R.string.cancel),
            showable = remember { rm }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainUI()
            }
        }
    }
}