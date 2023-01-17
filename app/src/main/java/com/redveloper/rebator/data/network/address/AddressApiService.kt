package com.redveloper.rebator.data.network.address

import com.redveloper.rebator.data.network.address.response.ListProvinceResponseModel
import retrofit2.http.GET

interface AddressApiService {
    @GET("provinsi")
    suspend fun getProvince() : ListProvinceResponseModel
}