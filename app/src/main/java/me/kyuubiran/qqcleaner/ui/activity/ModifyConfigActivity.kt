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
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.view.AppBar
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
        return arrayListOf()
    }
}