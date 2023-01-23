package com.redveloper.rebator.data.network.seller

import com.google.firebase.firestore.FirebaseFirestore
import com.redveloper.rebator.data.network.seller.response.AppResponseSellerModel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AppSellerFirebaseImpl(
    private val firestore: FirebaseFirestore
): AppSellerFirebase {

    private val collectionSeller = firestore.collection("sellers")

    override suspend fun getSellers(): List<AppResponseSellerModel> {
        return suspendCoroutine { continuation ->
            collectionSeller
                .get()
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener { result ->
                    val sellers = arrayListOf<AppResponseSellerModel>()
                    for (document in result){
                        val responseModel = AppResponseSellerModel()
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
                        responseModel.status = document.data.get("status").toString()
                        sellers.add(responseModel)
                    }
                    continuation.resume(sellers)
                }
        }
    }

    override suspend fun searchSellers(query: String?): List<AppResponseSellerModel> {
        return suspendCoroutine { continuation ->
            val newCollection = if (!query.isNullOrBlank()){
                collectionSeller
                    .whereEqualTo("tiktok_id", query)
                    .get()
            } else {
                collectionSeller
                    .get()
            }

            newCollection
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener { result ->
                    val sellers = arrayListOf<AppResponseSellerModel>()
                    for (document in result){
                        val responseModel = AppResponseSellerModel()
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
                        responseModel.status = document.data.get("status").toString()
                        sellers.add(responseModel)
                    }
                    continuation.resume(sellers)
                }
        }
    }

    override suspend fun getDetailSeller(tiktokId: String): AppResponseSellerModel {
        return suspendCoroutine { continuation ->
            collectionSeller
                .document(tiktokId)
                .get()
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
                .addOnSuccessListener { result ->
                    result.data?.let { data ->
                        val responseModel = AppResponseSellerModel()
                        responseModel.akusisiName = data.get("akusisi").toString()
                        responseModel.tiktokId = data.get("tiktok_id").toString()
                        responseModel.officeName = data.get("office_name").toString()
                        responseModel.officeAddress = data.get("office_address").toString()
                        responseModel.officeProviceId = data.get("office_province_id").toString()
                        responseModel.officeProvinceName = data.get("office_province_name").toString()
                        responseModel.officeCityId = data.get("office_city_id").toString()
                        responseModel.officeCityName = data.get("office_city_name").toString()
                        responseModel.officeDistrictId = data.get("office_district_id").toString()
                        responseModel.officeDistrictName = data.get("office_district_name").toString()
                        responseModel.officePhotoUrl = data.get("office_photo_url").toString()
                        responseModel.sellerName = data.get("seller_name").toString()
                        responseModel.sellerPhoneNumber = data.get("seller_phone_number").toString()
                        responseModel.sellerGender = data.get("seller_gender").toString()
                        responseModel.timeStamp = data.get("timestamp").toString()
                        responseModel.status = data.get("status").toString()

                        continuation.resume(responseModel)
                    }
                }
        }
    }
}