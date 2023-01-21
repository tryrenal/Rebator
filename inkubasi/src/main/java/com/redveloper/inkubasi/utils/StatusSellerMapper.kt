package com.redveloper.inkubasi.utils

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