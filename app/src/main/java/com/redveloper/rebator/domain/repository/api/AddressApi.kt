package com.redveloper.rebator.domain.repository.api

import com.redveloper.rebator.domain.entity.KeyValue

interface AddressApi{
    suspend fun getProvince(): List<KeyValue>
    suspend fun getCitys(idProvince: Int): List<KeyValue>
    suspend fun getDistrict(idCity: Int): List<KeyValue>
}