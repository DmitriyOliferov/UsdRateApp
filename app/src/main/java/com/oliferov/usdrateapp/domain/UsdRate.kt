package com.oliferov.usdrateapp.domain

import java.util.*

data class UsdRate(
    val date: String = "-1",
    val nominal: Int = -1,
    val value: Double = -1.0
)
