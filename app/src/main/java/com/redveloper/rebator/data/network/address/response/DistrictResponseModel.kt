package com.redveloper.rebator.data.network.address.response

import com.google.gson.annotations.SerializedName

data class DistrictResponseModel(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("id_kota")
    val cityId: String?,
    @SerializedName("nama")
    val name: String?
)

data class ListDistrictResponseModel(
    @SerializedName("kecamatan")
    val districts: List<DistrictResponseModel>
)