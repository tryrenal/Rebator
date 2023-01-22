package com.redveloper.rebator.utils.mapper

import com.redveloper.rebator.domain.entity.StatusSeller

object StatusSellerMapper {
    fun getValueOfStatus(status: StatusSeller): String{
        return when(status){
            StatusSeller.DRAFT -> "Draft"
            StatusSeller.VISITED -> "Dikunjungi"
            StatusSeller.CONTACTED -> "Dihubungi"
        }
    }
}