package com.redveloper.inkubasi.utils.mapper

import com.redveloper.inkubasi.domain.entity.ResultVisit

object ResultVisitMapper {
    fun getValueOfResultVisit(visit: ResultVisit): String {
        return when(visit){
            ResultVisit.UNLINK -> "Lepas Tautan"
            ResultVisit.NOT_RESPON -> "Tidak Ada Respon"
            ResultVisit.AFILIASI -> "Afiliasi"
            ResultVisit.NOT_OWNER -> "Bukan Owner"
            ResultVisit.TTS_NOT_ACTIVE -> "TikTok Tidak Aktif"
            ResultVisit.TTS_ACTIVE -> "TikTok Aktif"
            ResultVisit.CHANGE_ACCOUNT -> "Akun TikTok Sudah Berubah"
            ResultVisit.DEACTIVE_ACCOUNT -> "Akun TikTok Sudah Tidak Aktif"
        }
    }
}