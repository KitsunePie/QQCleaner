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
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ProvideWindowInsets
import me.kyuubiran.qqcleaner.QQCleanerData.isDark
import me.kyuubiran.qqcleaner.QQCleanerData.isFirst
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTheme
import me.kyuubiran.qqcleaner.ui.util.noClick

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 这是设置布局
        setContent {
            QQCleanerTheme(QQCleanerData.theme) {
                ProvideWindowInsets {
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
                        QQCleanerApp()
                    }
                }
            }
        }
    }

}

