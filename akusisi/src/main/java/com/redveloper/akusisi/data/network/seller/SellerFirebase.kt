package com.redveloper.akusisi.data.network.seller

import android.net.Uri

interface SellerFirebase {
    suspend fun checkTiktokIdAvailable(tiktokId: String): Boolean
    suspend fun addSeller(documentId: String, data: HashMap<String, Any>): Boolean
    suspend fun addPhotoSeller(documentId: String, uri: Uri): String
}