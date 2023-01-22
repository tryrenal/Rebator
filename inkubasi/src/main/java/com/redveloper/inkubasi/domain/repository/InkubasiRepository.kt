package com.redveloper.inkubasi.domain.repository

import com.redveloper.inkubasi.data.network.inkubasi.InkubasiFirebase
import com.redveloper.inkubasi.domain.entity.Inkubasi
import com.redveloper.inkubasi.domain.entity.PotentialSeller
import com.redveloper.inkubasi.domain.entity.ResultVisit
import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class InkubasiRepository(
    private val inkubasiFirebase: InkubasiFirebase,
    private val crDispatcher: CrDispatcher
) {

    suspend fun checkIfInkubasiExist(tiktokId: String): Boolean {
        return CoroutineScope(crDispatcher.network()).async {
            inkubasiFirebase.checkIfInkubasiExist(tiktokId)
        }.await()
    }

    suspend fun getInkubasi(tiktokId: String): Inkubasi {
        return CoroutineScope(crDispatcher.network()).async {
            inkubasiFirebase.getInkubasi(tiktokId).let{
                Inkubasi(
                    visit = it.resultVisit?.let { it1 -> ResultVisit.valueOf(it1) },
                    potential = it.sellerPotential?.let { it1 -> PotentialSeller.valueOf(it1) }
                )
            }
        }.await()
    }
}