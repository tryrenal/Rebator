package com.redveloper.rebator.data.network.address.response

import com.google.gson.annotations.SerializedName

data class ProvinceResponseModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("nama")
    val nama: String?
)

data class ListProvinceResponseModel(
    @SerializedName("provinsi")
    val provinsi: List<ProvinceResponseModel>
)