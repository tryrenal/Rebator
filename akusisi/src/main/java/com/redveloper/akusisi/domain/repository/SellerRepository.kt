package com.redveloper.akusisi.domain.repository

import com.redveloper.akusisi.data.network.seller.SellerFirebase
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
}