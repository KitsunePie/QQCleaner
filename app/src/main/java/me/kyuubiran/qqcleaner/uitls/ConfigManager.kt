package me.kyuubiran.qqcleaner.uitls

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

// 获取设置的 dataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

// 主题所对应的键值
val THEME_SELECT = intPreferencesKey("theme_select")

// 纯黑主题所对应的键值
val IS_BLACK_THEME = booleanPreferencesKey("theme_select")
