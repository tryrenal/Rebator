package com.redveloper.rebator.data.network.address

import com.redveloper.rebator.data.network.address.response.ListCityResponseModel
import com.redveloper.rebator.data.network.address.response.ListProvinceResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressApiService {
    @GET("provinsi")
    suspend fun getProvince() : ListProvinceResponseModel

    @GET("kota")
    suspend fun getCitys(
        @Query("id_provinsi") id: Int
    ): ListCityResponseModel
}