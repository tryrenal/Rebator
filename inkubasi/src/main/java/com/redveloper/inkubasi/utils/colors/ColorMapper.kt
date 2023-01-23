package com.redveloper.inkubasi.utils.colors

import com.redveloper.inkubasi.domain.entity.PotentialSeller
import com.redveloper.inkubasi.domain.entity.ResultVisit
import com.redveloper.rebator.domain.entity.StatusSeller
import com.redveloper.inkubasi.R as InkubasiR

object ColorMapper {

    fun setColorStatus(status: StatusSeller): Int {
        return when(status){
            StatusSeller.DRAFT -> InkubasiR.color.red
            StatusSeller.CONTACTED -> InkubasiR.color.blue
            StatusSeller.VISITED -> InkubasiR.color.green
        }
    }

    fun setColorResultVisit(visit: ResultVisit): Int {
        return when(visit){
            ResultVisit.UNLINK -> InkubasiR.color.orange
            ResultVisit.NOT_RESPON -> InkubasiR.color.red
            ResultVisit.AFILIASI -> InkubasiR.color.orange
            ResultVisit.NOT_OWNER -> InkubasiR.color.old_blue
            ResultVisit.TTS_NOT_ACTIVE -> InkubasiR.color.red
            ResultVisit.TTS_ACTIVE -> InkubasiR.color.green
            ResultVisit.CHANGE_ACCOUNT -> InkubasiR.color.blue
            ResultVisit.DEACTIVE_ACCOUNT -> InkubasiR.color.red
        }
    }

    fun setColorPotential(visit: PotentialSeller): Int {
        return when(visit){
            PotentialSeller.POTENTIAL -> InkubasiR.color.green
            PotentialSeller.NOT_POTENTIAL -> InkubasiR.color.red
            PotentialSeller.AFILIASI -> InkubasiR.color.orange
        }
    }
}