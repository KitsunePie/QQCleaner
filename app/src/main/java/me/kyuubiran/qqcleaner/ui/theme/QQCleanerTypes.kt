package me.kyuubiran.qqcleaner.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily.Companion.Default
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

object QQCleanerTypes {
    val cardTitleTextStyle = intoTextStyle(fontSize = 18.sp, lineHeight = 26.sp, fontWeight = Bold)
    val itemTextStyle = intoTextStyle(fontSize = 16.sp, lineHeight = 16.sp, fontWeight = Normal)
    val TitleTextStyle = intoTextStyle(fontSize = 24.sp, lineHeight = 26.sp, fontWeight = Normal)
    val SubTitleTextStyle = intoTextStyle(fontSize = 18.sp, lineHeight = 26.sp, fontWeight = Bold)
    val ButtonTitleTextStyle =
        intoTextStyle(fontSize = 8.sp, lineHeight = 26.sp, fontWeight = Normal)
    val TitleStyle = intoTextStyle(fontSize = 20.sp, lineHeight = 24.sp, fontWeight = Bold)
    val DialogTitleStyle = intoTextStyle(fontSize = 18.sp, lineHeight = 24.sp, fontWeight = Bold)
    val DialogEditStyle = intoTextStyle(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = Normal)
    val DialogButtonStyle = intoTextStyle(fontSize = 16.sp, lineHeight = 24.sp, fontWeight = Normal)
}

private fun intoTextStyle(
    fontFamily: SystemFontFamily = Default,
    fontWeight: FontWeight? = Normal,
    fontSize: TextUnit,
    textAlign: TextAlign = Start,
    lineHeight: TextUnit
) = TextStyle(
    fontFamily = fontFamily,
    fontWeight = fontWeight,
    fontSize = fontSize,
    textAlign = textAlign,
    lineHeight = lineHeight
)
