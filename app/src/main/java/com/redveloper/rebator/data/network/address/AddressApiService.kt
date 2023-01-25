package com.redveloper.rebator.data.network.address

import com.redveloper.rebator.data.network.address.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AddressApiService {
    @GET("provinsi")
    suspend fun getProvince() : ListProvinceResponseModel

    @GET("kota")
    suspend fun getCitys(
        @Query("id_provinsi") id: Int
    ): ListCityResponseModel

    @GET("kecamatan")
    suspend fun getDistricts(
        @Query("id_kota") id: Int
    ): ListDistrictResponseModel

    @GET("provinsi/{id}")
    suspend fun getDetailProvince(
        @Path("id") id: Int
    ): ProvinceResponseModel

    @GET("kota/{id}")
    suspend fun getDetailCity(
        @Path("id") id: Int
    ): CityResponseModel

    @GET("kecamatan/{id}")
    suspend fun getDetailDistrict(
        @Path("id") id: Int
    ): DistrictResponseModel
}