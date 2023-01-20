package com.redveloper.akusisi.utils

import com.redveloper.akusisi.R
import java.util.*

object ColorUtils {

    fun setCollorView(date: Date): Int{
        val calender = Calendar.getInstance()
        calender.time = date
        val intMonth = calender.get(Calendar.MONTH)
        return when(intMonth){
            1 -> R.color.color_january
            2 -> R.color.color_february
            3 -> R.color.color_march
            4 -> R.color.color_april
            5 -> R.color.color_may
            6 -> R.color.color_june
            7 -> R.color.color_july
            8 -> R.color.color_august
            9 -> R.color.color_september
            10 -> R.color.color_october
            11-> R.color.color_november
            12 -> R.color.color_december
            else -> R.color.color_december
        }
    }
}