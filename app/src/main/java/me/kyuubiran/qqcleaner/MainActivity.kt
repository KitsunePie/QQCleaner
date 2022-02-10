package me.kyuubiran.qqcleaner

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.QQCleanerData.isDark
import me.kyuubiran.qqcleaner.QQCleanerData.isFirst
import me.kyuubiran.qqcleaner.QQCleanerData.navigationBarHeight
import me.kyuubiran.qqcleaner.QQCleanerData.statusBarHeight
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTheme
import me.kyuubiran.qqcleaner.ui.util.noClick
import me.kyuubiran.qqcleaner.ui.util.px2dp

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 这是设置布局
        setContent {
            QQCleanerTheme(QQCleanerData.theme) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .noClick()
                        .background(QQCleanerColorTheme.colors.appBarsAndItemBackgroundColor)
                ) {
                    LaunchedEffect(isDark) {
                        setLightMode(!isDark)
                    }
                    AnimatedVisibility(isFirst, modifier = Modifier.align(Alignment.Center)) {
                        Image(
                            modifier = Modifier
                                .size(88.dp)
                                .align(Alignment.Center),
                            painter = painterResource(
                                id = if (isDark)
                                    R.drawable.ic_home_qqcleaner_dark
                                else
                                    R.drawable.ic_home_qqcleaner
                            ),
                            contentDescription = stringResource(id = R.string.icon_content_description),
                        )
                    }
                    // 状态栏高度是为了各个页面不和状态栏重叠
                    statusBarHeight = getStatusBarHeight()
                    // 底部栏高度是为了弹窗不和底部栏重叠
                    navigationBarHeight = getNavigationBarHeight()
                    QQCleanerApp()
                }
            }
        }
    }

    private fun Float.px2dp(): Float {
        return this.px2dp(this@MainActivity)
    }

    /**
     * 返回状态栏的高度
     */
    private fun getStatusBarHeight(): Dp {
        val res = applicationContext.resources
        val resourceId = res
            .getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
        return if (resourceId > 0) // 这个是 px 需要转换
            (res.getDimension(resourceId).px2dp()).dp
        else 0f.dp
    }

    /**
     * 返回导航栏的高度
     */
    private fun getNavigationBarHeight(): Dp {
        val res = applicationContext.resources
        val resourceId = res
            .getIdentifier(
                "navigation_bar_height",
                "dimen",
                "android"
            )
        return if (resourceId > 0)  // 这个是 px 需要转换
            (res.getDimension(resourceId).px2dp()).dp
        else 0.dp
    }

}

