package com.redveloper.inkubasi.utils.mapper

import com.redveloper.inkubasi.domain.entity.StatusSeller

object StatusSellerMapper {
    fun getValueOfStatus(status: StatusSeller): String{
        return when(status){
            StatusSeller.DRAFT -> "Draft"
            StatusSeller.VISITED -> "Dikunjungi"
            StatusSeller.CONTACTED -> "Dihubungi"
        }
    }
}