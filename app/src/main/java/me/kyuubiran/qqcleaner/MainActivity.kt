package me.kyuubiran.qqcleaner

import android.content.res.Configuration
import android.os.Bundle
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
import me.kyuubiran.qqcleaner.QQCleanerData.isDark
import me.kyuubiran.qqcleaner.QQCleanerData.navigationBarHeight
import me.kyuubiran.qqcleaner.QQCleanerData.statusBarHeight
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.activity.BaseActivity
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.*
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTheme
import me.kyuubiran.qqcleaner.ui.utils.px2dp
import me.kyuubiran.qqcleaner.util.navigationBarMode
import me.kyuubiran.qqcleaner.util.statusBarLightMode

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
                    // 状态栏高度是为了各个页面不和状态栏重叠
                    statusBarHeight = getStatusBarHeight()
                    // 底部栏高度是为了弹窗不和底部栏重叠
                    navigationBarHeight = getNavigationBarHeight()
                    QQCleanerApp()
                }
                // 通过当前主题去修改颜色
                LaunchedEffect(QQCleanerData.theme) {
                    isDark = when (QQCleanerData.theme) {
                        Light -> {
                            false
                        }
                        Dark -> {
                            true
                        }
                        System -> {
                            isNightMode()
                        }
                    }

                    if (isDark) {
                        statusBarLightMode(false)
                        navigationBarMode(false)
                    } else {
                        statusBarLightMode()
                        navigationBarMode()
                    }
                }
            }
        }
    }

    private fun isNightMode(): Boolean {
        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }

    private fun Float.px2dp(): Float {
        return this.px2dp(this@MainActivity)
    }

    /**
     * 返回状态栏的高度
     */
    private fun getStatusBarHeight(): Dp {
        var height = 0f
        val resourceId = applicationContext.resources
            .getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
        if (resourceId > 0) {
            // 这个是 px 需要转换
            height = applicationContext.resources.getDimension(resourceId)
        }

        return (height.px2dp()).dp
    }

    /**
     * 返回导航栏的高度
     */
    private fun getNavigationBarHeight(): Dp {
        var height = 0f
        val resourceId =
            applicationContext.resources
                .getIdentifier(
                    "navigation_bar_height",
                    "dimen",
                    "android"
                )
        if (resourceId > 0) {
            // 这个是 px 需要转换
            height = applicationContext.resources.getDimension(resourceId)
        }

        return (height.px2dp()).dp
    }

}

