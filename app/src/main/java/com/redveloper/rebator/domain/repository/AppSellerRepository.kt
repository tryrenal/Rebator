package com.redveloper.rebator.domain.repository

import com.redveloper.rebator.data.network.seller.AppSellerFirebase
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.Seller
import com.redveloper.rebator.domain.entity.StatusSeller
import com.redveloper.rebator.utils.date.DateUtils
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class AppSellerRepository(
    private val appSellerFirebase: AppSellerFirebase,
    private val crDispatcher: CrDispatcher
) {

    suspend fun getSeller() : List<Seller>{
        return CoroutineScope(crDispatcher.network()).async {
            appSellerFirebase.getSellers().map {
                Seller(
                    akusisiName = it.akusisiName,
                    tiktokId = it.tiktokId,
                    officeName = it.officeName,
                    officeAddress = it.officeAddress,
                    officeProviceId = it.officeProviceId?.toIntOrNull(),
                    officeProvinceName = it.officeProvinceName,
                    officeCityId = it.officeCityId?.toIntOrNull(),
                    officeCityName = it.officeCityName,
                    officeDistrictId = it.officeDistrictId?.toIntOrNull(),
                    officeDistrictName = it.officeDistrictName,
                    officePhotoUrl = it.officePhotoUrl,
                    sellerName = it.sellerName,
                    sellerPhoneNumber = it.sellerPhoneNumber,
                    sellerGender = it.sellerGender?.let { it1 -> Gender.valueOf(it1) },
                    timeStamp = it.timeStamp?.let { it1 -> DateUtils.convertToDate(it1) },
                    status = it.status?.let { it1 -> StatusSeller.valueOf(it1) }
                )
            }
        }.await()
    }

    suspend fun getDetailSeller(tiktokId: String): Seller{
        return CoroutineScope(crDispatcher.network()).async {
            appSellerFirebase.getDetailSeller(tiktokId).let {
                Seller(
                    akusisiName = it.akusisiName,
                    tiktokId = it.tiktokId,
                    officeName = it.officeName,
                    officeAddress = it.officeAddress,
                    officeProviceId = it.officeProviceId?.toIntOrNull(),
                    officeProvinceName = it.officeProvinceName,
                    officeCityId = it.officeCityId?.toIntOrNull(),
                    officeCityName = it.officeCityName,
                    officeDistrictId = it.officeDistrictId?.toIntOrNull(),
                    officeDistrictName = it.officeDistrictName,
                    officePhotoUrl = it.officePhotoUrl,
                    sellerName = it.sellerName,
                    sellerPhoneNumber = it.sellerPhoneNumber,
                    sellerGender = it.sellerGender?.let { it1 -> Gender.valueOf(it1) },
                    timeStamp = it.timeStamp?.let { it1 -> DateUtils.convertToDate(it1) },
                    status = it.status?.let { it1 -> StatusSeller.valueOf(it1) }
                )
            }
        }.await()
    }
}