package com.redveloper.akusisi.data.network.seller

import android.net.Uri
import com.redveloper.akusisi.data.network.seller.response.ResponseSellerModel
import com.redveloper.akusisi.domain.entity.Seller

interface SellerFirebase {
    suspend fun getSellers(): List<ResponseSellerModel>
    suspend fun checkTiktokIdAvailable(tiktokId: String): Boolean
    suspend fun addSeller(documentId: String, data: HashMap<String, Any>): Boolean
    suspend fun addPhotoSeller(documentId: String, uri: Uri): String
}