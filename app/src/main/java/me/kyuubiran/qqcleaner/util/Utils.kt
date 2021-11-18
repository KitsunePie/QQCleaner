package me.kyuubiran.qqcleaner.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun <T> rememberMutableStateOf(value: T) = remember {
    mutableStateOf(value)
}

fun Context.jumpUri(uriString: String) {
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uriString)))
}

fun Context.jumpUri(uri: Uri) {
    this.startActivity(Intent(Intent.ACTION_VIEW, uri))
}