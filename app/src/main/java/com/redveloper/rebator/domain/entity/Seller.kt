package com.redveloper.rebator.domain.entity

import java.util.*

data class Seller(
    var akusisiName: String? = null,
    var tiktokId: String? = null,
    var officeName: String? = null,
    var officeAddress: String? = null,
    var officeProviceId: Int? = null,
    var officeProvinceName: String? = null,
    var officeCityId: Int? = null,
    var officeCityName: String? = null,
    var officeDistrictId: Int? = null,
    var officeDistrictName: String? = null,
    var officePhotoUrl: String? = null,
    var sellerName: String? = null,
    var sellerPhoneNumber: String? = null,
    var sellerGender: Gender? = null,
    var timeStamp: Date? = null,
    var status: StatusSeller? = null
)