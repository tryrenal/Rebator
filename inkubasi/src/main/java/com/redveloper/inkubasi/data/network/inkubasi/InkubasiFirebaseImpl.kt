package com.redveloper.inkubasi.data.network.inkubasi

import com.google.firebase.firestore.FirebaseFirestore
import com.redveloper.inkubasi.data.network.inkubasi.response.InkubasiResponseModel
import com.redveloper.inkubasi.domain.entity.SellerInkubasi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class InkubasiFirebaseImpl(
    private val firestore: FirebaseFirestore
) : InkubasiFirebase{

    private val collectionInkubasi = firestore.collection("inkubasi")

    override suspend fun checkIfInkubasiExist(tiktokId: String): Boolean {
        return collectionInkubasi
            .document(tiktokId)
            .get()
            .await().exists()
    }

    override suspend fun getInkubasi(tiktokId: String): InkubasiResponseModel {
        return suspendCoroutine { continuation ->
            collectionInkubasi
                .document(tiktokId)
                .get()
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener { result ->
                    result.data?.let { data ->
                        val responseModel = InkubasiResponseModel()
                        responseModel.resultVisit = data.get("result_visit").toString()
                        responseModel.sellerPotential = data.get("seller_potential").toString()

                        continuation.resume(responseModel)
                    }
                }
        }
    }
}