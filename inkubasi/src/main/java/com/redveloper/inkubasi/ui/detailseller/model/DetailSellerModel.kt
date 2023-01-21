package com.redveloper.inkubasi.ui.detailseller.model

import com.redveloper.inkubasi.domain.entity.StatusSeller
import com.redveloper.rebator.domain.entity.Gender
import java.util.*

data class DetailSellerModel(
    val photoUrl: String?,
    val status: StatusSeller,
    val joinDate: Date?,
    val officeAddress: String?,
    val officeProvince: String?,
    val officeCity: String?,
    val officeDistrict: String?,
    val sellerName: String?,
    val sellerPhoneNumber: String?,
    val sellerGender: Gender?,
    val tiktokId: String?
)