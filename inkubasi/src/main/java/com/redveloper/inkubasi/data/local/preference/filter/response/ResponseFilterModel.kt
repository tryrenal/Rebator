package com.redveloper.inkubasi.data.local.preference.filter.response

data class ResponseFilterModel(
    var provinceId: Int? = null,
    var cityId: Int? = null,
    var districtId: Int? = null,
    var genderMan: Boolean? = null,
    var genderWoman: Boolean? = null,
    var statusDraft: Boolean? = null,
    var statusContacted: Boolean? = null,
    var statusVisited: Boolean? = null
)