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

fun getLastCleanTimeText(time: Long): String {
    return SimpleDateFormat("MM/dd HH:mm:ss", Locale.getDefault()).format(Date(time))
}

fun getFormatCleanTimeText(time: Long): String {
    val diff = System.currentTimeMillis() - time
    var days = diff / 86400000L
    if (days == 0L) {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val weekday1 = calendar.get(Calendar.DAY_OF_WEEK)
        calendar.time = Date(time)
        val weekday2 = calendar.get(Calendar.DAY_OF_WEEK)
        days += (weekday1 - weekday2)
    }
    return when (days) {
        0L -> " 今天"
        1L -> " 昨天"
        2L -> " 前天"
        in 3L..Long.MAX_VALUE -> " $days 天前"
        else -> "未来的 ${-days} 天后"
    }
}