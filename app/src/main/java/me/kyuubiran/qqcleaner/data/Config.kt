package me.kyuubiran.qqcleaner.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Config(
    val title: String,
    val author: String,
    val enable: Boolean,
    val hostApp: String,
    val content: List<ConfigContent>
)

@JsonClass(generateAdapter = true)
data class ConfigContent(
    val title: String,
    val enable: Boolean,
    @Json(name = "path") val pathList: List<Path>
)

@JsonClass(generateAdapter = true)
data class Path(
    val prefix: String,
    val suffix: String
)