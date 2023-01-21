package com.redveloper.rebator.data.network.seller

import com.redveloper.rebator.data.network.seller.response.AppResponseSellerModel

interface AppSellerFirebase {
    suspend fun getSellers(): List<AppResponseSellerModel>
    suspend fun getDetailSeller(tiktokId: String): AppResponseSellerModel
}