package me.kyuubiran.qqcleaner.uitls

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val THEME_SELECT = intPreferencesKey("theme_select")

val IS_BLACK_THEME = booleanPreferencesKey("theme_select")
