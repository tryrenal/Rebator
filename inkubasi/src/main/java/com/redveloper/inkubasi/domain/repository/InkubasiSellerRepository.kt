package com.redveloper.inkubasi.domain.repository

import android.net.Uri
import com.redveloper.inkubasi.data.network.updateseller.UpdateSeller
import com.redveloper.inkubasi.domain.entity.SellerInkubasi
import com.redveloper.rebator.domain.entity.StatusSeller
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class InkubasiSellerRepository(
    private val updateSeller: UpdateSeller,
    private val crDispatcher: CrDispatcher
) {

    suspend fun addPhotoSeller(docId: String, photoUri: Uri, inkubasiName: String): String{
        return CoroutineScope(crDispatcher.network()).async {
            updateSeller.addPhoto(docId, photoUri, inkubasiName)
        }.await()
    }

    suspend fun addInkubasi(model: SellerInkubasi): Boolean{
        return CoroutineScope(crDispatcher.network()).async {
            val data = hashMapOf<String, Any>(
                "inkubasi_name" to model.inkubasiName!!,
                "photo_url" to model.photoUrl!!,
                "inkubasi_date" to model.inkubasiDate!!,
                "tiktok_id" to model.tiktokId!!,
                "result_visit" to model.resultVisit!!,
                "seller_potential" to model.sellerPotential!!,
                "note" to model.note
            )
            updateSeller.updateSeller(tiktokId = model.tiktokId, data)
        }.await()
    }

    suspend fun updateStatus(tiktokId: String, status: StatusSeller): Boolean{
        return CoroutineScope(crDispatcher.network()).async {
            val data = hashMapOf<String, Any>(
                "status" to status.name,
            )
            updateSeller.updateStatus(tiktokId, data)
        }.await()
    }
}