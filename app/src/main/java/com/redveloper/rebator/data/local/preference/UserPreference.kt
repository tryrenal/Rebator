package com.redveloper.rebator.data.local.preference

import kotlinx.coroutines.flow.Flow

interface UserPreference {
    suspend fun saveUserID(uid: String)
    suspend fun getUserID(): Flow<String?>
    suspend fun deleteUserID()
}