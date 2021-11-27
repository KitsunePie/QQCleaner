package me.kyuubiran.qqcleaner.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.ItemText

@Composable
fun ItemCard(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
fun ItemMenuCard(text: String, contentDescription: String) {
    ItemCard() {
        ItemText(text = text, Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_forward),
            contentDescription = contentDescription,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
    }
}

@Composable
fun ItemTextCard(text: String, contentDescription: String) {
    ItemCard() {
        ItemText(text = text, Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_forward),
            contentDescription = contentDescription,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
    }
}

@Composable
fun ItemSwitchCard(text: String, contentDescription: String) {
    ItemCard() {
        ItemText(text = text, Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_toggle_on),
            contentDescription = contentDescription,
            modifier = Modifier
                .height(24.dp)
                .width(36.dp)
        )
    }
}

@Composable
fun ItemMoreCard(text: String, contentDescription: String) {
    ItemCard() {
        ItemText(text = text, Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_forward),
            contentDescription = contentDescription,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
    }
}