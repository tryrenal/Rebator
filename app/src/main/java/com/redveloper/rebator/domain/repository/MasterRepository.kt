package com.redveloper.rebator.domain.repository

import com.redveloper.rebator.domain.entity.KeyValue
import com.redveloper.rebator.domain.repository.api.AddressApi
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class MasterRepository(
    private val addressApi: AddressApi,
    private val crDispatcher: CrDispatcher
) {

    suspend fun getProvinces() : List<KeyValue>{
        return CoroutineScope(crDispatcher.network()).async {
            addressApi.getProvince()
        }.await()
    }

    suspend fun getCitys(idProvince: Int): List<KeyValue>{
        return CoroutineScope(crDispatcher.network()).async {
            addressApi.getCitys(idProvince)
        }.await()
    }
}