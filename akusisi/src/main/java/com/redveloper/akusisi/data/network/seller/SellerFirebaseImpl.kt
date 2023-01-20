package com.redveloper.akusisi.data.network.seller

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.redveloper.akusisi.data.network.seller.response.ResponseSellerModel
import com.redveloper.akusisi.domain.entity.Seller
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SellerFirebaseImpl(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
): SellerFirebase {

    private val collectionSeller = firestore.collection("sellers")
    private val storageRef = storage.reference

    override suspend fun getSellers(): List<ResponseSellerModel> {
        return suspendCoroutine { continuation ->
            collectionSeller
                .get()
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener { result ->
                    val sellers = arrayListOf<ResponseSellerModel>()
                    for (document in result){
                        val responseModel = ResponseSellerModel()
                        responseModel.akusisiName = document.data.get("akusisi").toString()
                        responseModel.tiktokId = document.data.get("tiktok_id").toString()
                        responseModel.officeName = document.data.get("office_name").toString()
                        responseModel.officeAddress = document.data.get("office_address").toString()
                        responseModel.officeProviceId = document.data.get("office_province_id").toString()
                        responseModel.officeProvinceName = document.data.get("office_province_name").toString()
                        responseModel.officeCityId = document.data.get("office_city_id").toString()
                        responseModel.officeCityName = document.data.get("office_city_name").toString()
                        responseModel.officeDistrictId = document.data.get("office_district_id").toString()
                        responseModel.officeDistrictName = document.data.get("office_district_name").toString()
                        responseModel.officePhotoUrl = document.data.get("office_photo_url").toString()
                        responseModel.sellerName = document.data.get("seller_name").toString()
                        responseModel.sellerPhoneNumber = document.data.get("seller_phone_number").toString()
                        responseModel.sellerGender = document.data.get("seller_gender").toString()
                        responseModel.timeStamp = document.data.get("timestamp").toString()
                        sellers.add(responseModel)
                    }
                    continuation.resume(sellers)
                }
        }
    }

    override suspend fun checkTiktokIdAvailable(tiktokId: String): Boolean {
        return collectionSeller
            .document(tiktokId)
            .get()
            .await()
            .exists()
    }

    override suspend fun addSeller(documentId: String, data: HashMap<String, Any>): Boolean {
        return suspendCoroutine { continuation ->
            collectionSeller
                .document(documentId)
                .set(data)
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener {
                    continuation.resume(true)
                }
        }
    }

    override suspend fun addPhotoSeller(documentId: String, uri: Uri): String {
        return suspendCoroutine { continuation ->
            val storageSeller = storageRef.child("sellers/$documentId")
            val uploadTask: UploadTask = storageSeller.putFile(uri)

            uploadTask.addOnFailureListener{
                continuation.resumeWithException(it)
            }.addOnSuccessListener {
                it.task.continueWithTask { task ->
                    if (!task.isSuccessful){
                        task.exception?.let {
                            continuation.resumeWithException(it)
                        }
                    }
                    return@continueWithTask storageSeller.downloadUrl
                }.addOnCompleteListener {
                    if (it.isSuccessful){
                        val downloadUrl = it.result
                        continuation.resume(downloadUrl.toString())
                    }
                }
            }
        }
    }
}