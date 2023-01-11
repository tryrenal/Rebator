package com.redveloper.akusisi.ui.addseller.model

import java.io.File
import java.io.Serializable

data class AddSellerModel(
    var akusisiName: String? = null,
    var tiktokID: String? = null,
    var officeName: String? = null,
    var officeAddress: String? = null,
    var officeProvince: String? = null,
    var officeCity: String? = null,
    var officeDistrict: String? = null,
    var officePhoto: File? = null,
    var sellerName: String? = null,
    var sellerPhoneNumber: String? = null,
): Serializable