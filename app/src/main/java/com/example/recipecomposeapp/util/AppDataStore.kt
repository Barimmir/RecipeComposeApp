package com.example.recipecomposeapp.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object PreferencesKeys {
    val FAVORITE_RECIPE_IDS = stringSetPreferencesKey("favorite_recipe_ids")
    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "recipe_app_prefs"
)
