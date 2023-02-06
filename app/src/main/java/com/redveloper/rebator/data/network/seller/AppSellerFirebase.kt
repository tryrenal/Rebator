package com.redveloper.rebator.data.network.seller

import com.redveloper.rebator.data.network.seller.response.AppResponseSellerModel
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.StatusSeller

interface AppSellerFirebase {
    suspend fun getSellers(): List<AppResponseSellerModel>
    suspend fun getDetailSeller(tiktokId: String): AppResponseSellerModel
    suspend fun searchSellers(query: String?, genders: List<Gender>?, status: List<StatusSeller>?,
                              provinceId: Int?, cityId: Int?, districtId: Int?): List<AppResponseSellerModel>
}