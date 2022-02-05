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
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.Theme.*
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
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
                        .background(colors.background)
                ) {
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
                // 通过当前主题去修改颜色
                LaunchedEffect(QQCleanerData.theme) {
                    // 因为有一个跟随系统，所以需要判断当前是否为暗色主题
                    isDark = when (QQCleanerData.theme) {
                        Light -> false
                        Dark -> true
                        System -> isNightMode()
                    }
                    // 当主题变化的时候设置状态栏
                    setLightMode(!isDark)
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

