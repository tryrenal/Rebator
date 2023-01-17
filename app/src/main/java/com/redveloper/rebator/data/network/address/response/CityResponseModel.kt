package com.redveloper.rebator.data.network.address.response

import com.google.gson.annotations.SerializedName

data class CityResponseModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_provinsi")
    val idProvince: String,
    @SerializedName("nama")
    val name: String
)

data class ListCityResponseModel(
    @SerializedName("kota_kabupaten")
    val citys: List<CityResponseModel>
)