package com.redveloper.rebator.data.network.seller

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.redveloper.rebator.data.network.seller.response.AppResponseSellerModel
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.StatusSeller
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

    override suspend fun searchSellers(
        query: String?,
        genders: List<Gender>?,
        status: List<StatusSeller>?,
        provinceId: Int?,
        cityId: Int?,
        districtId: Int?
    ): List<AppResponseSellerModel> {
        return suspendCoroutine { continuation ->
            var newCollection : Query = firestore.collection("sellers")
            if (!query.isNullOrBlank()){
                newCollection = newCollection.whereEqualTo("tiktok_id", query)
            }
            if (!genders.isNullOrEmpty()){
                newCollection = newCollection.whereIn("seller_gender", genders.map { it.name })
            }
            if (!status.isNullOrEmpty()){
                newCollection = newCollection.whereIn("status", status.map { it.name })
            }
            if (provinceId != null){
                newCollection = newCollection.whereEqualTo("office_province_id",provinceId)
            }
            if (cityId != null){
                newCollection = newCollection.whereEqualTo("office_city_id", cityId)
            }
            if (districtId != null){
                newCollection = newCollection.whereEqualTo("office_district_id", districtId)
            }

            newCollection
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val result = it.result
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
                        Log.i("tiktok_id_length", sellers.size.toString())
                        continuation.resume(sellers)
                    } else {
                        it.exception?.let { it1 -> continuation.resumeWithException(it1) }
                    }
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