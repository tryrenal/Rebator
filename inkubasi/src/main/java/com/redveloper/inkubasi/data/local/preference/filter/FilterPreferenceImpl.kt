package com.redveloper.inkubasi.data.local.preference.filter

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.redveloper.inkubasi.data.local.preference.filter.request.RequestFilterModel
import com.redveloper.inkubasi.data.local.preference.filter.response.ResponseFilterModel
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.StatusSeller
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilterPreferenceImpl(
    private val context: Context
) : FilterPreference {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "FilterPreference")

    private val provinceId = intPreferencesKey("idProvince")
    private val cityId = intPreferencesKey("idCity")
    private val districtId = intPreferencesKey("idDistrict")
    private val genderMan = booleanPreferencesKey("man")
    private val genderWoman = booleanPreferencesKey("woman")
    private val statusDraft = booleanPreferencesKey("draft")
    private val statusContacted = booleanPreferencesKey("contacted")
    private val statusVisited = booleanPreferencesKey("visited")

    override suspend fun saveFilter(data: RequestFilterModel) {
        context.dataStore.edit { pref ->
            data.provinceId?.let{
                pref[provinceId] = it
            }
            data.cityId?.let {
                pref[cityId] = it
            }
            data.districtId?.let{
                pref[districtId] = it
            }
            data.genders?.let {
                pref[genderMan] = it.contains(Gender.MAN.name)
                pref[genderWoman] = it.contains(Gender.WOMAN.name)
            }
            data.status?.let {
                pref[statusDraft] = it.contains(StatusSeller.DRAFT.name)
                pref[statusContacted] = it.contains(StatusSeller.CONTACTED.name)
                pref[statusVisited] = it.contains(StatusSeller.VISITED.name)
            }
        }
    }

    override suspend fun getFilter(): Flow<ResponseFilterModel> {
        return context.dataStore.data.map {
            ResponseFilterModel(
                provinceId = it[provinceId],
                cityId = it[cityId],
                districtId = it[districtId],
                genderMan = it[genderMan],
                genderWoman = it[genderWoman],
                statusDraft = it[statusDraft],
                statusContacted = it[statusContacted],
                statusVisited = it[statusVisited]
            )
        }
    }

    override suspend fun deleteFilter() {
        context.dataStore.edit { it.clear() }
    }
}