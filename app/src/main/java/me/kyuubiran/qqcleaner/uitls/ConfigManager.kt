package me.kyuubiran.qqcleaner.uitls

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

// 获取设置的 dataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

// 主题所对应的键值
val THEME_SELECT = intPreferencesKey("theme_select")

// 纯黑主题所对应的键值
val IS_BLACK_THEME = booleanPreferencesKey("is_black_theme")

// 自动清理的键值
val IS_AUTO_CLEANER = booleanPreferencesKey("auto_cleaner")

// 自动清理间隔时间的键值
val AUTO_CLEANER_TIME = intPreferencesKey("auto_cleaner_time")


suspend fun <T> Context.editData(preferences: Preferences.Key<T>, value: T) {
    dataStore.edit { settings ->
        settings[preferences] = value
    }
}
