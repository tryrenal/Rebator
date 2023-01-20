package com.redveloper.rebator.utils.date

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTimestamp(format: String = "yyyy-MM-dd HH:mm:ss"): String{
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun convertToDate(date: String): Date? {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date)
    }
}