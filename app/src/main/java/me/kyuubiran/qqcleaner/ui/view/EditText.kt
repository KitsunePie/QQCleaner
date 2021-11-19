package me.kyuubiran.qqcleaner.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditText(
    text: String = "",
    label: String = "",
    textColor: Color = Color.Black,
    fontSize: TextUnit = 18.sp,
    onChange: ((String) -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    kbOpts: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = text,
        label = { Text(text = label) },
        textStyle = TextStyle(color = textColor, fontSize = fontSize),
        onValueChange = { onChange?.invoke(it) },
        keyboardOptions = kbOpts,
        singleLine = singleLine,
        maxLines = maxLines,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )
}

@Preview
@Composable
private fun View() {
    EditText(text = "str")
}