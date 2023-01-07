package com.redveloper.rebator.data.local.preference.onboarding

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OnBoardingPreferenceImpl(
    private val context: Context
) : OnBoardingPreference{

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "OnBoarding")

    private val onBoardingStatusPref = booleanPreferencesKey("onBoardingStatus")

    override suspend fun saveOnBoardingStatus(status: Boolean) {
        context.dataStore.edit {
            it[onBoardingStatusPref] = status
        }
    }

    override suspend fun getOnBoardingStatus(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[onBoardingStatusPref] ?: false
        }
    }

    override suspend fun deleteOnBoardingStatus() {
        context.dataStore.edit {
            it.clear()
        }
    }
}