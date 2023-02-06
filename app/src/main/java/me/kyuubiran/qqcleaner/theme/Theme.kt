package me.kyuubiran.qqcleaner.theme

import android.content.Context
import android.content.res.Configuration


data class Theme(private val value: Int, val isBlack: Boolean) {
    val type: Type
        get() {
            return when (value) {
                0 -> Type.LIGHT_THEME
                1 -> Type.DARK_THEME
                2 -> Type.AUTO_THEME
                else -> Type.LIGHT_THEME
            }
        }
    enum class Type(val value: Int) {
        LIGHT_THEME(0),
        DARK_THEME(1),
        AUTO_THEME(2)
    }

    fun getColorPalette(context: Context): QQCleanerColors {
        return when (type) {
            Type.LIGHT_THEME -> LightColorPalette
            Type.DARK_THEME -> if (isBlack) BlackColorPalette else DarkColorPalette
            Type.AUTO_THEME -> when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> LightColorPalette
                Configuration.UI_MODE_NIGHT_YES -> if (isBlack) BlackColorPalette else DarkColorPalette
                else -> LightColorPalette
            }
        }

    }
}