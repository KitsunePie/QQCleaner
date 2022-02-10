package me.kyuubiran.qqcleaner.ui.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes

@Composable
fun TopBar(click: () -> Unit, titleText: String) {
    TopBar(click = click, titleText = titleText) {

    }
}

@Composable
fun TopBar(backClick: () -> Unit, iconClick: () -> Unit, titleText: String, @DrawableRes id: Int) {
    TopBar(click = backClick, titleText = titleText) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .clickable {
                    iconClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = id),
                contentDescription = "返回",
                modifier = Modifier.size(24.dp),
                tint = colors.secondTextColor
            )
        }
    }
}

@Composable
fun TopBar(click: () -> Unit, titleText: String, content: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .clickable {
                    click()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "返回",
                modifier = Modifier.size(24.dp),
                tint = colors.firstTextColor
            )
        }

        Text(
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            style = QQCleanerTypes.TitleStyle,
            text = titleText,
            color = colors.firstTextColor
        )
        content()

    }
}