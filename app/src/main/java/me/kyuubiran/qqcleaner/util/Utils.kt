package me.kyuubiran.qqcleaner.util

import android.content.Context
import android.content.Intent
import android.icu.math.BigDecimal
import android.net.Uri
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun <T> rememberMutableStateOf(value: T) = remember {
    mutableStateOf(value)
}

fun Modifier.onLongClick(
    onLongClick: (() -> Unit)? = null
) = composed {
    this.pointerInput(Unit) {
        detectTapGestures(onLongPress = { onLongClick?.invoke() })
    }
}

fun Modifier.onDoubleClick(
    onDoubleClick: (() -> Unit)? = null
) = composed {
    this.pointerInput(Unit) {
        detectTapGestures(onDoubleTap = { onDoubleClick?.invoke() })
    }
}

fun Context.jumpUri(uriString: String) {
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uriString)))
}

fun Context.jumpUri(uri: Uri) {
    this.startActivity(Intent(Intent.ACTION_VIEW, uri))
}

fun getFormatCleanedSize(): String {
    val cleaned = ConfigManager.sTotalCleaned
    val sl = BigDecimal(cleaned)
    val b: BigDecimal
    val result: Double
    return when (cleaned.toString().length) {
        in 0..3 -> {
            "$sl Byte"
        }
        in 4..6 -> {
            b = sl.divide(BigDecimal(1_024.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            "$result KiB"
        }
        in 7..9 -> {
            b = sl.divide(BigDecimal(1_048_576.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            "$result MiB"
        }
        in 10..12 -> {
            b = sl.divide(BigDecimal(1_073_741_824.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            "$result GiB"
        }
        in 12..99 -> {
            b = sl.divide(BigDecimal(1_099_511_627_776.0))
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
            "$result TiB"
        }
        else -> {
            ""
        }
    }
}