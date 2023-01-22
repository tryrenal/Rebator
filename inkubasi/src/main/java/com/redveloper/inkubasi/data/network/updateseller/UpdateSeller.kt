package com.redveloper.inkubasi.data.network.updateseller

import android.net.Uri

interface UpdateSeller {
    suspend fun addPhoto(tiktokId: String, uri: Uri, inkubasiName: String): String
    suspend fun updateSeller(tiktokId: String, data: HashMap<String, Any>): Boolean
    suspend fun updateStatus(tiktokId: String, data: HashMap<String, Any>): Boolean
}