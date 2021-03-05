package me.kyuubiran.qqcleaner.utils

import android.util.Log

private const val TAG = "QQCleaner"

fun loge(exception: Exception) {
    Log.e(TAG, "", exception)
}

fun loge(throwable: Throwable) {
    Log.e(TAG, "", throwable)
}

fun loge(msg: String) {
    Log.e(TAG, msg)
}

fun logi(info: String) {
    Log.i(TAG, info)
}

/* 日志工具类 Top-Level */