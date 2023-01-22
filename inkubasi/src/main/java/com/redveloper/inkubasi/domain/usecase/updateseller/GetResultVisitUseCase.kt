package com.redveloper.inkubasi.domain.usecase.updateseller

import com.redveloper.inkubasi.domain.entity.ResultVisit
import com.redveloper.inkubasi.utils.mapper.ResultVisitMapper

class GetResultVisitUseCase {
    fun getListResultVisit(list: (ArrayList<Pair<String, String>>) -> Unit){
        val listData = arrayListOf(
            Pair(ResultVisit.UNLINK.name, ResultVisitMapper.getValueOfResultVisit(ResultVisit.UNLINK)),
            Pair(ResultVisit.NOT_RESPON.name, ResultVisitMapper.getValueOfResultVisit(ResultVisit.NOT_RESPON)),
            Pair(ResultVisit.AFILIASI.name, ResultVisitMapper.getValueOfResultVisit(ResultVisit.AFILIASI)),
            Pair(ResultVisit.NOT_OWNER.name, ResultVisitMapper.getValueOfResultVisit(ResultVisit.NOT_OWNER)),
            Pair(ResultVisit.TTS_NOT_ACTIVE.name, ResultVisitMapper.getValueOfResultVisit(ResultVisit.TTS_NOT_ACTIVE)),
            Pair(ResultVisit.TTS_ACTIVE.name, ResultVisitMapper.getValueOfResultVisit(ResultVisit.TTS_ACTIVE)),
            Pair(ResultVisit.CHANGE_ACCOUNT.name, ResultVisitMapper.getValueOfResultVisit(ResultVisit.CHANGE_ACCOUNT)),
            Pair(ResultVisit.DEACTIVE_ACCOUNT.name, ResultVisitMapper.getValueOfResultVisit(ResultVisit.DEACTIVE_ACCOUNT)),
        )
        list.invoke(listData)
    }
}