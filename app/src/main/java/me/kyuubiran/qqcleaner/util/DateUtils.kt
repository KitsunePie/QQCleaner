package me.kyuubiran.qqcleaner.util

import android.icu.text.SimpleDateFormat
import java.util.*

private fun isBelong(beg: String, end: String): Boolean {
    val df = SimpleDateFormat("HH:mm", Locale.getDefault())
    val cNow = Calendar.getInstance().apply {
        time = df.parse(df.format(Date()))
    }
    val cBeg = Calendar.getInstance().apply {
        time = df.parse(beg)
    }
    val cEnd = Calendar.getInstance().apply {
        time = df.parse(end)
    }
    return cNow.before(cEnd) && cNow.after(cBeg)
}

/**
 * 获取当前时间对应的标语
 */
fun getCurrentTimeText(): String {
    return when {
        isBelong("00:00", "04:59") -> "夜深了，"
        isBelong("05:00", "10:59") -> "早上好，"
        isBelong("11:00", "12:59") -> "中午好，"
        isBelong("13:00", "17:59") -> "下午好，"
        isBelong("18:00", "23:59") -> "晚上好，"
        else -> ""
    }
}