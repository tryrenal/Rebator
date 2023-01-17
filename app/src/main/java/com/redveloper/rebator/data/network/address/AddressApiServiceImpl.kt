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

    override suspend fun getCitys(idProvince: Int): List<KeyValue> {
        return addressApi.getCitys(idProvince).citys.map {
            KeyValue(key = it.id.toString(), value = it.name)
        }
    }

    override suspend fun getDistrict(idCity: Int): List<KeyValue> {
        return addressApi.getDistricts(idCity).districts.map {
            KeyValue(key = it.id.toString(), value = it.name)
        }
    }
}