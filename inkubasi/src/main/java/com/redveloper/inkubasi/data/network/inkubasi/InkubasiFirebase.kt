package com.redveloper.inkubasi.data.network.inkubasi

import com.redveloper.inkubasi.data.network.inkubasi.response.InkubasiResponseModel

interface InkubasiFirebase {
    suspend fun checkIfInkubasiExist(tiktokId: String): Boolean
    suspend fun getInkubasi(tiktokId: String): InkubasiResponseModel
}