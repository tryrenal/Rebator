package com.redveloper.inkubasi.domain.usecase.updateseller

import com.redveloper.inkubasi.domain.entity.PotentialSeller
import com.redveloper.inkubasi.utils.mapper.PotentialSellerMapper

class GetPotentialSellerUseCase() {
    fun getListPotential(list: (ArrayList<Pair<String, String>>) -> Unit){
        val listData = arrayListOf(
            Pair(PotentialSeller.POTENTIAL.name, PotentialSellerMapper.getValueOfPotential(PotentialSeller.POTENTIAL)),
            Pair(PotentialSeller.NOT_POTENTIAL.name, PotentialSellerMapper.getValueOfPotential(PotentialSeller.NOT_POTENTIAL)),
            Pair(PotentialSeller.AFILIASI.name, PotentialSellerMapper.getValueOfPotential(PotentialSeller.AFILIASI)),
        )
        list.invoke(listData)
    }
}