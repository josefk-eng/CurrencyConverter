package com.josef.currency.domain

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.text.format.DateUtils
import java.util.*

fun currentDate(format:String):String{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat(format, Locale.getDefault())
            .format(Date(System.currentTimeMillis()))
    } else {
        TODO("VERSION.SDK_INT < N")
    }
}

fun getDaysAgo(daysAgo: Int, format: String): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat(format, Locale.getDefault())
            .format(calendar.time)
    } else {
        TODO("VERSION.SDK_INT < N")
    }
}