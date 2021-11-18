package me.kyuubiran.qqcleaner.ui.view

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppBar(
    text: String
) {
    TopAppBar(modifier = Modifier.fillMaxHeight(0.06f), backgroundColor = Color.White) {
        Text(
            modifier = Modifier.padding(start = 15.dp),
            text = text,
            color = Color.Black,
            fontSize = 23.sp
        )
    }
}