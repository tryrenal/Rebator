package com.redveloper.inkubasi.data.local.preference.filter

import com.redveloper.inkubasi.data.local.preference.filter.request.RequestFilterModel
import com.redveloper.inkubasi.data.local.preference.filter.response.ResponseFilterModel
import kotlinx.coroutines.flow.Flow

interface FilterPreference {
    suspend fun saveFilter(data: RequestFilterModel)
    suspend fun getFilter(): Flow<ResponseFilterModel>
    suspend fun deleteFilter()
}