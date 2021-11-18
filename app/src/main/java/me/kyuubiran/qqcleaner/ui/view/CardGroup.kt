package me.kyuubiran.qqcleaner.ui.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.kyuubiran.qqcleaner.ui.theme.Blue200

@Composable
fun CardGroup(@StringRes title: Int, content: @Composable () -> Unit) {
    CardGroup(title = LocalContext.current.getString(title), content = content)
}

@Composable
fun CardGroup(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 30.dp),
        shape = RoundedCornerShape(13.dp)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                text = title,
                color = Blue200,
                fontSize = 20.sp
            )
            content()
        }
    }
}