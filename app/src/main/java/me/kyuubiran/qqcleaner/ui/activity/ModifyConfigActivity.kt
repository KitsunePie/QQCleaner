package me.kyuubiran.qqcleaner.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.github.kyuubiran.ezxhelper.utils.Log
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.data.CleanData
import me.kyuubiran.qqcleaner.ui.view.AppBar
import me.kyuubiran.qqcleaner.ui.view.CleanDataCard
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
                        val data = CleanData(it)
                        CleanDataCard(cleanData = data)
                    }
                }
            }
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

    private fun readFiles(): ArrayList<File> {
        getConfigDir()
        genDefaultConfig()
        val arr = ArrayList<File>()
        val dir = File("${CommonPath.dDataDir}/qqcleaner")
        if (dir.isDirectory) {
            dir.listFiles()?.let {
                arr.addAll(it)
            }
        }
        return arr
    }

    private fun genDefaultConfig() {
        try {
            val f = File("${CommonPath.dDataDir}/qqcleaner/Default.json")
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
                      "regexp": false,
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