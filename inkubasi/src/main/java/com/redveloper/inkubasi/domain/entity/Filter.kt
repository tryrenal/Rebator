package com.redveloper.inkubasi.domain.entity

data class Filter(
    var provinceId: Int? = null,
    var cityId: Int? = null,
    var districtId: Int? = null,
    var genderMan: Boolean? = null,
    var genderWoman: Boolean? = null,
    var statusDraft: Boolean? = null,
    var statusContacted: Boolean? = null,
    var statusVisited: Boolean? = null
)