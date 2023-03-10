package com.redveloper.akusisi.domain.repository

import android.net.Uri
import com.redveloper.akusisi.data.network.seller.SellerFirebase
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class SellerRepository(
    private val sellerFirebase: SellerFirebase,
    private val crDispatcher: CrDispatcher
) {

    suspend fun checkSellerIsExist(tiktokId: String): Boolean {
        return CoroutineScope(crDispatcher.network()).async {
            sellerFirebase.checkTiktokIdAvailable(tiktokId)
        }.await()
    }

    suspend fun addSeller(addSellerModel: AddSellerModel): Boolean{
        return CoroutineScope(crDispatcher.network()).async {
            val data = hashMapOf<String, Any>(
                "akusisi" to addSellerModel.akusisiName!!,
                "tiktok_id" to addSellerModel.tiktokID!!,
                "office_name" to addSellerModel.officeName!!,
                "office_address" to addSellerModel.officeAddress!!,
                "office_province_id" to addSellerModel.officeProvinceId!!,
                "office_province_name" to addSellerModel.officeProvinceName!!,
                "office_city_id" to addSellerModel.officeCityId!!,
                "office_city_name" to addSellerModel.officeCityName!!,
                "office_district_id" to addSellerModel.officeDistrictId!!,
                "office_district_name" to addSellerModel.officeDistrictName!!,
                "office_photo_url" to addSellerModel.officePhotoUrl!!,
                "seller_name" to addSellerModel.sellerName!!,
                "seller_phone_number" to addSellerModel.sellerPhoneNumber!!,
                "seller_gender" to addSellerModel.sellerGender!!,
                "timestamp" to addSellerModel.timestamp!!,
                "status" to addSellerModel.status!!
            )
            sellerFirebase.addSeller(documentId = addSellerModel.tiktokID!!, data)
        }.await()
    }

    suspend fun addPhotoSeller(docId: String, photoUri: Uri, sellerName: String): String{
        return CoroutineScope(crDispatcher.network()).async {
            sellerFirebase.addPhotoSeller(docId, photoUri, sellerName)
        }.await()
    }
}