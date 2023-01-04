package com.redveloper.rebator.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferenceImpl (
    private val context: Context
) : UserPreference{

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "UserId")

    private val prefId = stringPreferencesKey("id")

    override suspend fun saveUserID(uid: String) {
        context.dataStore.edit { pref ->
            pref[prefId] = uid
        }
    }

    override suspend fun getUserID(): Flow<String?> {
        return context.dataStore.data.map {
            it[prefId]
        }
    }

    override suspend fun deleteUserID() {
        context.dataStore.edit { it.clear() }
    }
}