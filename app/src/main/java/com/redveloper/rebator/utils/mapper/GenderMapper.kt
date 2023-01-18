package com.redveloper.rebator.utils.mapper

import com.redveloper.rebator.domain.entity.Gender

object GenderMapper {
    fun getValueOfGender(gender: Gender): String {
        return when(gender){
            Gender.MAN -> "Laki-laki"
            Gender.WOMAN -> "Perempuan"
        }
    }

    fun getGenderByValue(value: String): Gender? {
        return when(value){
            "Laki-laki" -> Gender.MAN
            "Perempuan" -> Gender.WOMAN
            else -> null
        }
    }
}