package me.kyuubiran.qqcleaner.ui.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.ColorPrimary

@Composable
fun AppBar(
    text: String
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxHeight(0.06f)
            .padding(bottom = 4.dp),
        backgroundColor = ColorPrimary
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 23.sp
            )
        }
    }
}


@Composable
fun AppBar(
    @StringRes text: Int
) {
    AppBar(text = stringResource(id = text))
}

@Preview
@Composable
private fun View() {
    Column(
        Modifier
            .height(1280.dp)
            .fillMaxWidth()
    ) {
        AppBar(text = R.string.app_name)
    }
}