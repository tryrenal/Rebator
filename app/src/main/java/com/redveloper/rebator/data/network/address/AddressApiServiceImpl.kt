package com.redveloper.rebator.data.network.address

import com.redveloper.rebator.domain.entity.KeyValue
import com.redveloper.rebator.domain.repository.api.AddressApi

class AddressApiServiceImpl(
    private val addressApi: AddressApiService
) : AddressApi {
    override suspend fun getProvince(): List<KeyValue> {
        return addressApi.getProvince().provinsi.map {
            KeyValue(key = it.id.toString(), value = it.nama)
        }
    }
}