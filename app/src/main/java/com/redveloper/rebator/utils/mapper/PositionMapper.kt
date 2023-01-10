package com.redveloper.rebator.utils.mapper

import com.redveloper.rebator.domain.entity.Position

object PositionMapper {
    fun getValueOfPosition(position: Position): String{
        return when(position){
            Position.INKUBASI -> "Inkubasi"
            Position.AKUSISI -> "Akusisi"
        }
    }

    fun getPositionByValue(value: String): Position?{
        return when(value){
            "Inkubasi" -> Position.INKUBASI
            "Akusisi" -> Position.AKUSISI
            else -> null
        }
    }
}