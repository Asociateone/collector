package com.example.collecter.services

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.collecter.enums.DataStoreKeys
import kotlinx.coroutines.flow.map


class PreferenceDataStore (
    private val context: Context
) {
    val Context.dataStore by preferencesDataStore(name = "user_preferences")

    val apiKey = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(DataStoreKeys.API_KEY.name)]
    }

    suspend fun update(key: DataStoreKeys, value: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key.name)] = value
        }
    }

}