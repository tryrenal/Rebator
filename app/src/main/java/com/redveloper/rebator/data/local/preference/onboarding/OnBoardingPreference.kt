package com.redveloper.rebator.data.local.preference.onboarding

import kotlinx.coroutines.flow.Flow

interface OnBoardingPreference {
    suspend fun saveOnBoardingStatus(status: Boolean)
    suspend fun getOnBoardingStatus(): Flow<Boolean>
    suspend fun deleteOnBoardingStatus()
}