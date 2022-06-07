package com.oliferov.usdrateapp.domain

interface RepositoryUsdRate {
    suspend fun getUsdRateForTodayAndYesterday(yesterday: Int, today: Int): List<UsdRate>

    suspend fun getUsdRatePerMonth(dateBeginningMonth: String, dateEndMonth: String): List<UsdRate>
}