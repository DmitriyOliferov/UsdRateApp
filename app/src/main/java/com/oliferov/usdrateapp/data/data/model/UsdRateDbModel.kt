package com.oliferov.usdrateapp.data.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usd_rate")
data class UsdRateDbModel(
    @PrimaryKey
    val date: Int? = -1,
    val nominal: Int? = -1,
    val value: Int? = -1
)