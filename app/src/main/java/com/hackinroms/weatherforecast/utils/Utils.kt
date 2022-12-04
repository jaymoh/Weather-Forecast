package com.hackinroms.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timeStamp: Int, format: String = "EEE, MMM d"): String {
    val date = Date(timeStamp.toLong() * 1000)
    val sdf = SimpleDateFormat(format)

    return sdf.format(date)
}

fun formatTime(timeStamp: Int, format: String = "HH:mm aa"): String {
    val date = Date(timeStamp.toLong() * 1000)
    val sdf = SimpleDateFormat(format)

    return sdf.format(date)
}

fun formatDecimals(number: Double, decimalPoints: Int = 0): String {
    return String.format("%.${decimalPoints}f", number)
}
