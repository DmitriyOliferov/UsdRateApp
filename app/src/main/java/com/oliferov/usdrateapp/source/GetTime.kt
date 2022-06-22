package com.oliferov.usdrateapp.source

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentAndPastMonth(): Pair<String, String> {
    val today = Calendar.getInstance()
    val pastMonth = Calendar.getInstance().apply {
        add(Calendar.MONTH, -1)
    }
    val format = SimpleDateFormat(FORMAT_FOR_MONTH)
    return format.format(pastMonth.time) to format.format(today.time)
}

fun getCurrentTime(): String {
    val today = Calendar.getInstance()
    val format = SimpleDateFormat(FORMAT_FOR_DAY)
    return format.format(today.time)
}

private const val FORMAT_FOR_MONTH = "dd/MM/yyyy"
private const val FORMAT_FOR_DAY = "dd.MM.yyyy"