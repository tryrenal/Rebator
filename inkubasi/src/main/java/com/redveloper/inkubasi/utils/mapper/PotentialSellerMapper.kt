package com.redveloper.inkubasi.utils.mapper

import com.redveloper.inkubasi.domain.entity.PotentialSeller

object PotentialSellerMapper {
    fun getValueOfPotential(potential: PotentialSeller): String{
       return when(potential){
            PotentialSeller.POTENTIAL -> "Seller Berpotensi di Edukasi"
            PotentialSeller.NOT_POTENTIAL -> "Tidak Berpotensi"
            PotentialSeller.AFILIASI -> "Seller Afiliasi"
        }
    }
}