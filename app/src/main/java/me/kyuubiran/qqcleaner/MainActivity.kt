package me.kyuubiran.qqcleaner

import android.content.res.Resources.getSystem
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import me.kyuubiran.qqcleaner.QQCleanerData.statusBarHeight
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.activity.BaseActivity
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTheme

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDecorFitsSystemWindows(window, false)

        setContent {
            QQCleanerTheme(QQCleanerData.theme) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .clickable(
                            // 防止击穿
                            onClick = {},
                            // 去掉点击水波纹
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .background(colors.background)
                ) {

                    LaunchedEffect(colors) {
                        Log.d("测试股", "onCreate:这里修改了 ")
                    }
                    QQCleanerApp()
                }
            }
        }
        statusBarHeight = getStatusBarHeight()
        // 缺少一个状态栏的颜色控制
    }

    /**
     * 返回状态栏的高度
     */
    private fun getStatusBarHeight(): Dp {
        var height = 0f
        val resourceId =
            applicationContext.resources
                .getIdentifier(
                    "status_bar_height",
                    "dimen",
                    "android"
                )
        if (resourceId > 0) {
            // 这个是 px 需要转换
            height = applicationContext.resources.getDimension(resourceId)
        }

        return (height / getSystem().displayMetrics.density).dp
    }

}

