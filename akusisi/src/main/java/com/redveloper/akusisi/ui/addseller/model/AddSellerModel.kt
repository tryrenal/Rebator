package com.redveloper.akusisi.ui.addseller.model

import java.io.File
import java.io.Serializable

data class AddSellerModel(
    var akusisiName: String? = null,
    var tiktokID: String? = null,
    var officeName: String? = null,
    var officeAddress: String? = null,
    var officeProvinceId: Int? = null,
    var officeProvinceName: String? = null,
    var officeCityId: Int? = null,
    var officeCityName: String? = null,
    var officeDistrictId: Int? = null,
    var officeDistrictName: String? = null,
    var officePhoto: File? = null,
    var sellerName: String? = null,
    var sellerPhoneNumber: String? = null,
): Serializable