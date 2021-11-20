package me.kyuubiran.qqcleaner.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.view.AppBar
import me.kyuubiran.qqcleaner.ui.view.CleanDataCard
import me.kyuubiran.qqcleaner.ui.view.MultiFabItem
import me.kyuubiran.qqcleaner.ui.view.MultiFloatingActionButton
import me.kyuubiran.qqcleaner.util.CleanManager.getConfigDir
import me.kyuubiran.qqcleaner.util.path.CommonPath
import java.io.File

class ModifyConfigActivity : BaseActivity() {
    private val fileLists by lazy {
        readFiles()
    }

    @Composable
    fun MainUI() {
        val scroller = rememberScrollState()
        val lists = remember {
            mutableStateOf(fileLists)
        }

        //主UI
        Column {
            AppBar(text = R.string.modify_config)

            Column(
                modifier = Modifier.scrollable(
                    state = scroller,
                    orientation = Orientation.Vertical
                )
            ) {
                if (lists.value.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(id = R.string.empty_config_text),
                            color = Color.Gray,
                            fontSize = 24.sp
                        )
                    }
                } else {
                    lists.value.forEach {
                        val data = it
                        CleanDataCard(cleanData = data)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(bottom = 50.dp, end = 15.dp)
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomEnd)
        ) {
            // 浮动按钮
            MultiFloatingActionButton(
                srcIcon = R.drawable.ic_baseline_add_24,
                items = listOf(
                    // 创建配置
                    MultiFabItem(
                        icon = painterResource(id = R.drawable.ic_baseline_add_24),
                        label = stringResource(id = R.string.create_config),
                        onClick = {

                        }
                    ),
                    // 从文件导入
                    MultiFabItem(
                        icon = painterResource(id = R.drawable.ic_baseline_import_24),
                        label = stringResource(id = R.string.import_from_file),
                        onClick = {

                        }
                    ),
                    // 从文件导入
                    MultiFabItem(
                        icon = painterResource(id = R.drawable.ic_baseline_content_paste_24),
                        label = stringResource(id = R.string.import_from_clipboard),
                        onClick = {

                        }
                    ),
                )
            )
        }
    }

    private fun saveConfig() {
        fileLists.forEach {
            it.save()
        }
    }

    override fun onStop() {
        saveConfig()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainUI()
            }
        }
    }

    private fun readFiles(): ArrayList<CleanData> {
        val dir = getConfigDir()
        genDefaultConfig()
        val arr = ArrayList<CleanData>()
        if (dir.isDirectory) {
            dir.listFiles()?.let { files ->
                files.forEach {
                    arr.add(CleanData(it))
                }
            }
        }
        return arr
    }

    private fun genDefaultConfig() {
        try {
            val f = File("${CommonPath.sAndroidDataDir}/qqcleaner/默认配置.json")
            if (f.exists()) return
            f.createNewFile()
            f.writeText(
                """
                {
                  "title": "默认配置",
                  "author": "KyuubiRan",
                  "enable": true,
                  "hostApp": "QQ",
                  "content": [
                    {
                      "title": "缓存",
                      "enable": false,
                      "path": [
                        "!AndroidData/Caches1",
                        "!AndroidData/Caches2"
                      ]
                    }
                  ]
                }
            """.trimIndent()
            )
        } catch (e: Exception) {
            Log.e(e)
        }
    }
}