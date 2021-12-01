package me.kyuubiran.qqcleaner.ui.theme

import cafe.adriel.lyricist.processor.Strings


@Strings(languageTag = Locales.EN)
val ENStrings = QQCleanerStrings(
    appName = "QQ Cleaner",
    titleSetup = "Set up",
    titleMore = "More",
    itemCleaner = "Automatic Cleaner",
    itemCleanerTime = "Cleaner Time",
    itemCleanerConfig = "Cleaner Config",
    itemTheme = "Theme",
    itemAbout = "About"
)

@Strings(languageTag = Locales.CN, default = true)
val CnString = QQCleanerStrings()

class QQCleanerStrings(
    val appName: String = "QQ 瘦身",
    val lastCleaner: (count: Int) -> String = { count ->
        val value = count.toString()
        "上次瘦身是 $value 天前"
    },
    val lastCleanerTime: (time: String) -> String = { time ->
        "上次瘦身是 $time"
    },
    val titleSetup: String = "设定",
    val titleMore: String = "更多",
    val itemCleaner: String = "自动瘦身",
    val itemCleanerTime: String = "瘦身时间",
    val itemCleanerConfig: String = "瘦身配置",
    val itemTheme: String = "主题风格",
    val itemAbout: String = "关于瘦身"
) {
}

object Locales {
    const val CN = "zh-CN"
    const val EN = "en"
}
