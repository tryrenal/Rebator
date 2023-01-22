package com.redveloper.inkubasi.domain.usecase.updateseller

import com.redveloper.rebator.domain.entity.StatusSeller
import com.redveloper.rebator.utils.mapper.StatusSellerMapper

class GetStatusSellerUseCase() {
    fun getListStatus(list: (ArrayList<Pair<String, String>>) -> Unit){
        val listData = arrayListOf(
            Pair(StatusSeller.DRAFT.name, StatusSellerMapper.getValueOfStatus(StatusSeller.DRAFT)),
            Pair(StatusSeller.VISITED.name, StatusSellerMapper.getValueOfStatus(StatusSeller.VISITED)),
            Pair(StatusSeller.CONTACTED.name, StatusSellerMapper.getValueOfStatus(StatusSeller.CONTACTED)),
        )
        list.invoke(listData)
    }
}