package com.redveloper.inkubasi.data.local.preference.filter.request

data class RequestFilterModel(
    var genders: List<String>? = null,
    var status: List<String>? = null,
    var provinceId: Int? = null,
    var cityId: Int? = null,
    var districtId: Int? = null
)