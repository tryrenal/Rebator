package com.redveloper.akusisi.ui.addseller.model

import android.net.Uri
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
    var officePhoto: Uri? = null,
    var officePhotoUrl: String? = null,
    var sellerName: String? = null,
    var sellerPhoneNumber: String? = null,
    var sellerGender: String? = null,
    var timestamp: String? = null,
    var status: String? = null
): Serializable