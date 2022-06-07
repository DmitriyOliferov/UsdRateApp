package com.oliferov.usdrateapp.domain

import java.util.*

data class UsdRate(
    val date: Date = Date(),
    val nominal: Int = -1,
    val value: Double = -1.0
)
